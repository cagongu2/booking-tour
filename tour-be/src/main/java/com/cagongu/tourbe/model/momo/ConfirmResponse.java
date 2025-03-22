package com.cagongu.tourbe.model.momo;

import com.cagongu.tourbe.enums.ConfirmRequestType;

public class ConfirmResponse extends Response {
    private Long amount;
    private Long transId;
    private String requestId;
    private ConfirmRequestType requestType;
}
