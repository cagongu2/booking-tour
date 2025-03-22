package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.AuthenticationRequest;
import com.cagongu.tourbe.dto.request.IntrospectRequest;
import com.cagongu.tourbe.dto.request.LogoutRequest;
import com.cagongu.tourbe.dto.request.RefreshRequest;
import com.cagongu.tourbe.dto.response.AuthenticationResponse;
import com.cagongu.tourbe.dto.response.IntrospectResponse;
import com.cagongu.tourbe.model.Account;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;


import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    void saveForgotPasswordCode(String code);
    boolean checkForgotPasswordCode(String code);

    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

    String generateToken(Account user);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    SignedJWT verifyToken(String token, Boolean isRefresh) throws JOSEException, ParseException;
}
