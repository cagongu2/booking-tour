package com.cagongu.tourbe.controller;

import com.cagongu.tourbe.dto.request.TourCreationRequest;
import com.cagongu.tourbe.dto.request.TourUpdateRequest;
import com.cagongu.tourbe.dto.response.ApiResponse;
import com.cagongu.tourbe.dto.response.TourRatingResponse;
import com.cagongu.tourbe.dto.response.TourResponse;
import com.cagongu.tourbe.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TourController {
    private static final String TOUR_PATH = "/tour";
    private static final String TOUR_PATH_ID = TOUR_PATH + "/{idTour}";
    private final TourService tourService;

    @GetMapping(TOUR_PATH)
    public ApiResponse<List<TourResponse>> getAll(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String place

    ) {

        return ApiResponse.<List<TourResponse>>builder()
                .result(tourService.listAll(description, type, place))
                .build();

    }

    @GetMapping(TOUR_PATH_ID)
    public ApiResponse<TourResponse> getById(@PathVariable("idTour") Long idTour) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.getById(idTour))
                .build();
    }

    @GetMapping(TOUR_PATH_ID+ "/get-tour-rating")
    public ApiResponse<TourRatingResponse> getTourRatingById(@PathVariable("idTour") Long idTour) {
        return ApiResponse.<TourRatingResponse>builder()
                .result(tourService.getTourRatingResponseById(idTour))
                .build();
    }

    @PostMapping(TOUR_PATH)
    public ApiResponse<TourResponse> createNewTour(@RequestBody TourCreationRequest request) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.create(request))
                .build();
    }



    @PutMapping(TOUR_PATH_ID)
    public ApiResponse<TourResponse> updateTour(@PathVariable("idTour") Long idTour, @RequestBody TourUpdateRequest request) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.update(idTour, request))
                .build();
    }

    @GetMapping(TOUR_PATH+ "/sorted-by-rating")
    public ApiResponse<Page<TourRatingResponse>> getToursSortedByRating(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("avgRating"), Sort.Order.desc("ratingCount")));

        return ApiResponse.<Page<TourRatingResponse>>builder()
                .result(tourService.getToursSortedByRating(pageable))
                .build();
    }

    @PutMapping(TOUR_PATH + "/add-favorite/{idTour}")
    public ApiResponse<TourResponse> addFavorite(@PathVariable("idTour") Long idTour,
                                                 @RequestParam("userId") Long userId) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.addFavorite(userId, idTour))
                .build();
    }

    @PutMapping(TOUR_PATH + "/remove-favorite/{idTour}")
    public ApiResponse<TourResponse> removeFavorite(@PathVariable("idTour") Long idTour,
                                                    @RequestParam("userId") Long userId) {
        return ApiResponse.<TourResponse>builder()
                .result(tourService.removeFavorite(userId, idTour))
                .build();
    }

    @DeleteMapping(TOUR_PATH_ID)
    public ApiResponse<TourResponse> deleteTour(@PathVariable("idTour") Long idTour){


        return ApiResponse.<TourResponse>builder()
                .result(tourService.delete(idTour))
                .build();
    }
}
