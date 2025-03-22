package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.EvaluateRequest;
import com.cagongu.tourbe.dto.response.EvaluateResponse;

import java.util.List;

public interface EvaluateService {
    EvaluateResponse createEvaluate(EvaluateRequest request);

    List<EvaluateResponse> getAllEvaluate();

    List<EvaluateResponse> getAllByTourId(Long tourId);
}
