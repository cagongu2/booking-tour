package com.cagongu.tourbe.dto.response;

import com.cagongu.tourbe.enums.StatusAction;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    Long id;
    String code;

     Double discountPercentage;
     Double maxDiscountAmount;
    int qualityOnHand;


    String description;

    boolean hidden;
    boolean active;

    Timestamp dateAdded;
    Timestamp dateEdited;
    Timestamp dateDeleted;

    StatusAction statusAction;

    Timestamp startDate;
    Timestamp endingDate;
}
