package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.PromotionCreationRequest;
import com.cagongu.tourbe.dto.request.PromotionUpdateRequest;
import com.cagongu.tourbe.dto.response.PromotionResponse;
import com.cagongu.tourbe.enums.StatusAction;
import com.cagongu.tourbe.exception.AppException;
import com.cagongu.tourbe.exception.ErrorCode;
import com.cagongu.tourbe.mapper.PromotionMapper;
import com.cagongu.tourbe.model.Promotion;
import com.cagongu.tourbe.repository.PromotionRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionMapper promotionMapper;
    private final PromotionRepository promotionRepository;

    @Override
    public List<PromotionResponse> getAllPromotion() {
        return promotionRepository.findAll().stream()
                .map(promotionMapper::promotionToPromotionResponse)
                .filter(promotionResponse -> promotionResponse.getStatusAction() != StatusAction.DELETE).toList();
    }

    @Override
    public PromotionResponse createNewPromotion(PromotionCreationRequest request) {
        Promotion promotion = promotionMapper.promotionRequestToPromotion(request);

        promotion.setCode(UUID.randomUUID().toString());

        promotionRepository.save(promotion);

        return promotionMapper.promotionToPromotionResponse(promotion);
    }

    @Override
    public PromotionResponse getById(Long id) {
        return promotionMapper.promotionToPromotionResponse(
                promotionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND)));
    }

    @Override
    public PromotionResponse updateBy(Long id, PromotionUpdateRequest request) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_NOT_FOUND));

        promotionMapper.updatePromotion(promotion, request);

        promotionRepository.save(promotion);

        return promotionMapper.promotionToPromotionResponse(promotion);
    }

    @Override
    public PromotionResponse deleteById(Long id) {
        return null;
    }
}
