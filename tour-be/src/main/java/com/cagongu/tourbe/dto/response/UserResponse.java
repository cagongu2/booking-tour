package com.cagongu.tourbe.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    Long idAccount;
    String name;
    String email;
    String phone;
    String address;
    String gender;
    Timestamp dob;
    String role;
    String username;
    String avatar;
    String idFacebook;
    String idGoogle;
    String website;
    int verify;
    String verifyToken;
    String statusAction;
    String payed;

    Timestamp dateAdded;
    Timestamp dateEdited;
    Timestamp dateDeleted;
}
