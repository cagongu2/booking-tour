package com.cagongu.tourbe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    TOUR_NOT_FOUND(1009, "Tour not existed", HttpStatus.NOT_FOUND),
    ORDER_NOT_EXISTED(1010, "Booking not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND(1011, "Post not existed", HttpStatus.NOT_FOUND),
    PROMOTION_NOT_FOUND(1012, "Promotion not existed", HttpStatus.NOT_FOUND),
    PASSWORD_CODE_NOT_FOUND(1013, "Password code not found", HttpStatus.NOT_FOUND);


    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
