package com.cagongu.tourbe.dto.response;

import com.cagongu.tourbe.model.CustomerInfo;
import com.cagongu.tourbe.model.Evaluate;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {
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
    Evaluate evaluate;

    String total;

    Set<CustomerInfo> customerInfoList;

    PromotionResponse promotion;
    UserResponse account;
    TourResponse tour;

    Timestamp dateAdded;
    Timestamp dateEdited;
    Timestamp dateDeleted;
}
