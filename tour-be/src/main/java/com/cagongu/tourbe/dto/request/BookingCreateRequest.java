package com.cagongu.tourbe.dto.request;

import com.cagongu.tourbe.model.CustomerInfo;
import com.cagongu.tourbe.model.Evaluate;
import com.cagongu.tourbe.model.Promotion;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class BookingCreateRequest {
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

    Promotion promotion;
    Set<CustomerInfo> customerInfoList;
}
