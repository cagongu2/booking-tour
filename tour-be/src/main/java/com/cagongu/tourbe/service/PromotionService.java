package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.PromotionCreationRequest;
import com.cagongu.tourbe.dto.request.PromotionUpdateRequest;
import com.cagongu.tourbe.dto.response.PromotionResponse;

import java.util.List;

public interface PromotionService {
    List<PromotionResponse> getAllPromotion();

    PromotionResponse createNewPromotion(PromotionCreationRequest request);

    PromotionResponse getById(Long id);

    PromotionResponse updateBy(Long id, PromotionUpdateRequest request);

    PromotionResponse deleteById(Long id);
}
