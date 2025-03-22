package com.cagongu.tourbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ConfirmTransactionRequest {
    private String orderId;
    private String requestId;
    private Long amount;
    @Builder.Default
    private String description = "";
}
