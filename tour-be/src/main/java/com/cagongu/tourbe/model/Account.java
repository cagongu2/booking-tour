package com.cagongu.tourbe.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAccount;

    String name;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String phone;
    String role;
    @Column(unique = true)
    String username;
    String password;

    String avatar;

    @Column(unique = true)
    String idFacebook;
    @Column(unique = true)
    String idGoogle;
    String website;

    String address;
    String gender;
    Timestamp dob;
    String payed;

    int verify;
    String verifyToken;

    String statusAction;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateAdded;
    @UpdateTimestamp
    Timestamp dateEdited;
    Timestamp dateDeleted;
}
