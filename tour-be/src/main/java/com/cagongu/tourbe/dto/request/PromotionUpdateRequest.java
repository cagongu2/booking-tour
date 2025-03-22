package com.cagongu.tourbe.dto.request;


import com.cagongu.tourbe.enums.StatusAction;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionUpdateRequest {
    Double discountPercentage;
    Double maxDiscountAmount;

    String description;
    int qualityOnHand;


    boolean hidden;
    boolean active;

    StatusAction statusAction;

    Timestamp startDate;
    Timestamp endingDate;
}
