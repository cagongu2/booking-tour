package com.cagongu.tourbe.controller;

import com.cagongu.tourbe.dto.request.PromotionCreationRequest;
import com.cagongu.tourbe.dto.request.PromotionUpdateRequest;
import com.cagongu.tourbe.dto.response.ApiResponse;
import com.cagongu.tourbe.dto.response.PromotionResponse;
import com.cagongu.tourbe.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PromotionController {
    private static final String PROMOTION_PATH = "/promotion";
    private static final String PROMOTION_PATH_ID = PROMOTION_PATH + "/{idPromotion}";
    private final PromotionService promotionService;

    @GetMapping(PROMOTION_PATH)
    public ApiResponse<List<PromotionResponse>> getAllPromotion(){
        return ApiResponse.<List<PromotionResponse>>builder()
                .result(promotionService.getAllPromotion())
                .build();
    }

    @GetMapping(PROMOTION_PATH_ID)
    public ApiResponse<PromotionResponse> getPromotionById(@PathVariable("idPromotion") Long idPromotion){
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.getById(idPromotion))
                .build();
    }

    @PostMapping(PROMOTION_PATH)
    public ApiResponse<PromotionResponse> createNewPromotion(@RequestBody PromotionCreationRequest request){
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.createNewPromotion(request))
                .build();
    }

    @PutMapping(PROMOTION_PATH_ID)
    public ApiResponse<PromotionResponse> updatePromotionById(@PathVariable("idPromotion") Long idPromotion, @RequestBody PromotionUpdateRequest request){
        return ApiResponse.<PromotionResponse>builder()
                .result(promotionService.updateBy(idPromotion, request))
                .build();
    }
}
