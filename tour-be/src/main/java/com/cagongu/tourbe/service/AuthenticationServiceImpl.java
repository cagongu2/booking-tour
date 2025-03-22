package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.AuthenticationRequest;
import com.cagongu.tourbe.dto.request.IntrospectRequest;
import com.cagongu.tourbe.dto.request.LogoutRequest;
import com.cagongu.tourbe.dto.request.RefreshRequest;
import com.cagongu.tourbe.dto.response.AuthenticationResponse;
import com.cagongu.tourbe.dto.response.IntrospectResponse;
import com.cagongu.tourbe.exception.AppException;
import com.cagongu.tourbe.exception.ErrorCode;
import com.cagongu.tourbe.model.Account;
import com.cagongu.tourbe.model.ForgotPassword;
import com.cagongu.tourbe.repository.AccountRepository;
import com.cagongu.tourbe.repository.PasswordRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    AccountRepository userRepository;
    PasswordRepository passwordRepository;

    @Value("${jwt.signerKey}")
    @NonFinal
    protected String SIGNER_KEY;

    @Value("${jwt.valid-duration}")
    @NonFinal
    protected Long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    @NonFinal
    protected Long REFRESHABLE_DURATION;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository
                .findUserByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public void saveForgotPasswordCode(String code) {
        passwordRepository.save(ForgotPassword.builder()
                .code(code)
                .active(true)
                .build());
    }

    @Override
    public boolean checkForgotPasswordCode(String code) {
        ForgotPassword forgotPassword = passwordRepository.findById(code).orElseThrow(() -> new AppException(ErrorCode.PASSWORD_CODE_NOT_FOUND));

        if (forgotPassword.getActive()) {
            forgotPassword.setActive(false);
            passwordRepository.save(forgotPassword);
            return true;
        }

        return false;
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public String generateToken(Account user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("NguyenThiKimTuyenService")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", user.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        return null;
    }

    @Override
    public SignedJWT verifyToken(String token, Boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

//        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
//            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
