package com.cagongu.tourbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourRatingResponse {
    private Long idTour;
    private String image;
    private String titleTour;
    private Double avgRating;
    private Long ratingCount;
}