package com.cagongu.tourbe.dto.request;


import com.cagongu.tourbe.enums.StatusAction;
import com.cagongu.tourbe.model.Booking;
import com.cagongu.tourbe.model.Favorite;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TourUpdateRequest {
    private Long idTour;

    String titleTour;
    double price;
    Double childPrice;
    Double babyPrice;
    double sale;

    Timestamp departureDate;// ngay khoi hanh
    Timestamp returnDate;
    String description;
    String address;
    String duration;// for instance: 1 ngay mot dem

    String type;

    Long tagId;
    Long serviceId;

    int views;
    int votes;
    int purchaseCount;

    StatusAction status;
    String statusAction;

    Set<Booking> bookings;

    Set<Favorite> favorites;

    String image;

}
