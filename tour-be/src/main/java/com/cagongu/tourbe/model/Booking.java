package com.cagongu.tourbe.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idOrder;

    int adult;
    int children;
    int baby;

    Boolean acceptPolice;

    String customerName;
    String address;
    String phone;
    String email;
    String notes;

    @OneToOne
    Evaluate evaluate;

    String total;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CustomerInfo> customerInfoList = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateAdded;
    @UpdateTimestamp
    Timestamp dateEdited;
    Timestamp dateDeleted;

    @ManyToOne
    Promotion promotion;

    @ManyToOne
    Account account;

    @ManyToOne
    Tour tour;
}
