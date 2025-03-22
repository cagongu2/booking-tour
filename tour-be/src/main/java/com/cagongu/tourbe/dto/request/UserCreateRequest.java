package com.cagongu.tourbe.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    String name;
    String email;
    String phone;
    String address;
    String gender;
    Timestamp dob;

    String role;
    String username;
    String password;

    String avatar;

    String idFacebook;
    String idGoogle;

    String website;

    int verify;
    String verifyToken;
    String statusAction;
}
