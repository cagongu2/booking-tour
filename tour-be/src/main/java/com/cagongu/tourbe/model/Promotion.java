package com.cagongu.tourbe.model;


import com.cagongu.tourbe.enums.StatusAction;
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
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false, unique = true)
    String code;

    private Double discountPercentage;
    private Double maxDiscountAmount;

    String description;
    int qualityOnHand;


    boolean hidden;
    boolean active;

    StatusAction statusAction;

    @Column(updatable = false)
    @CreationTimestamp
    Timestamp dateAdded;
    @UpdateTimestamp
    Timestamp dateEdited;
    Timestamp dateDeleted;

    Timestamp startDate;
    Timestamp endingDate;
}
