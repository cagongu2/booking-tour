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
public class PromotionCreationRequest {
    Double discountPercentage;
    Double maxDiscountAmount;

    String description;
    int qualityOnHand;


    @Builder.Default
    boolean hidden = false;

    @Builder.Default
    boolean active = true;

    @Builder.Default
    StatusAction statusAction = StatusAction.ACTIVE;

    Timestamp startDate;
    Timestamp endingDate;
}
