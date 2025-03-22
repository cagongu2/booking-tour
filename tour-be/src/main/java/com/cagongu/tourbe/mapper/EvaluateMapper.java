package com.cagongu.tourbe.mapper;


import com.cagongu.tourbe.dto.response.EvaluateResponse;
import com.cagongu.tourbe.model.Evaluate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EvaluateMapper {

    @Mapping(target = "accountId", source = "account.idAccount")
    @Mapping(target = "bookingId", source = "booking.idOrder")
    EvaluateResponse evaluateToResponse(Evaluate evaluate);
}
