package com.cagongu.tourbe.controller;

import com.cagongu.tourbe.dto.request.EvaluateRequest;
import com.cagongu.tourbe.dto.response.ApiResponse;
import com.cagongu.tourbe.dto.response.EvaluateResponse;
import com.cagongu.tourbe.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluates")
@RequiredArgsConstructor
public class EvaluateController {

    private final EvaluateService evaluateService;

    @PostMapping()
    public ApiResponse<EvaluateResponse> createEvaluate(@RequestBody EvaluateRequest request) {
        return ApiResponse.<EvaluateResponse>builder()
                .result(evaluateService.createEvaluate(request))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<EvaluateResponse>> getAll(){
        return ApiResponse.<List<EvaluateResponse>>builder()
                .result(evaluateService.getAllEvaluate())
                .build();
    }

    @GetMapping("/{tourId}")
    public ApiResponse<List<EvaluateResponse>> getAllByTourId(@PathVariable("tourId") Long tourId){
        return ApiResponse.<List<EvaluateResponse>>builder()
                .result(evaluateService.getAllByTourId(tourId))
                .build();
    }
}