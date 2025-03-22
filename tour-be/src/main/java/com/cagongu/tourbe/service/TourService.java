package com.cagongu.tourbe.service;


import com.cagongu.tourbe.dto.request.TourCreationRequest;
import com.cagongu.tourbe.dto.request.TourUpdateRequest;
import com.cagongu.tourbe.dto.response.TourRatingResponse;
import com.cagongu.tourbe.dto.response.TourResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TourService {
    List<TourResponse> listAll(String description, String type, String place);

    TourResponse getById(Long id);
    TourResponse create(TourCreationRequest request);
    TourResponse update(Long id, TourUpdateRequest request);
    TourResponse addFavorite(Long userId, Long tourId);
    TourResponse removeFavorite(Long userId, Long tourId);
    TourResponse delete(Long id);
    Page<TourRatingResponse> getToursSortedByRating(Pageable pageable);


    TourRatingResponse getTourRatingResponseById(Long id);
}
