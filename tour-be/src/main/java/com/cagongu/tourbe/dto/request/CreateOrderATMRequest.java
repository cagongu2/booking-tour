package com.cagongu.tourbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CreateOrderATMRequest {
    private String orderId;
    private String requestId;
    private Long amount;
    private String orderInfo;
    private String returnURL;
    private String notifyURL;
    private String extraData;
}
