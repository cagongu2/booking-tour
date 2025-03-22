package com.cagongu.tourbe.controller;

import com.nimbusds.jose.JOSEException;
import com.cagongu.tourbe.dto.request.*;
import com.cagongu.tourbe.dto.response.ApiResponse;
import com.cagongu.tourbe.dto.response.AuthenticationResponse;
import com.cagongu.tourbe.dto.response.IntrospectResponse;
import com.cagongu.tourbe.dto.response.UserResponse;
import com.cagongu.tourbe.service.AuthenticationService;
import com.cagongu.tourbe.service.EmailService;
import com.cagongu.tourbe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/identity/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestBody String email) {
        // Kiểm tra email và gửi token qua email

        String code = UUID.randomUUID().toString();

        EmailRequest request = EmailRequest.builder()
                .to(email)
                .subject("Mã khôi phục mật khẩu của bạn, vui lòng không cung cấp cho bất cứ ai.")
                .body(code)
                .build();

        emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());

        authenticationService.saveForgotPasswordCode(code);

        return ApiResponse.<String>builder()
                .result("Code gửi thành công!")
                .message("Email đã được gửi thành công!")
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            // Kiểm tra token
            boolean isValidToken = authenticationService.checkForgotPasswordCode(request.getResetCode());
            if (!isValidToken) {
                return ApiResponse.<String>builder()
                        .result("Mã khôi phục không hợp lệ hoặc đã hết hạn!")
                        .message("Mã khôi phục không hợp lệ hoặc đã hết hạn!")
                        .build();
            }

            // Kiểm tra email
            UserResponse userResponse = userService.readByEmail(request.getEmail());
            if (userResponse == null) {
                return ApiResponse.<String>builder()
                        .result("Không tìm thấy email người dùng!")
                        .message("Không tìm thấy email người dùng!")
                        .build();
            }

            // Cập nhật mật khẩu
            UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                    .password(request.getNewPassword()) // Nên mã hóa mật khẩu ở đây nếu chưa làm
                    .build();

            userService.update(userResponse.getIdAccount(), updateRequest);

             return ApiResponse.<String>builder()
                    .result("Mật khẩu đã được cập nhật thành công!")
                    .message("Mật khẩu đã được cập nhật thành công!")
                    .build();
        } catch (Exception e) {


            return ApiResponse.<String>builder()
                    .result( e.getMessage())
                    .message("Đã xảy ra lỗi khi cập nhật mật khẩu!")
                    .build();
        }
    }


    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    ApiResponse<Void> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        authenticationService.refreshToken(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
//    VerifyEmail
//    Reset password
}
