package com.cagongu.tourbe.mapper;

import com.cagongu.tourbe.dto.request.BookingCreateRequest;
import com.cagongu.tourbe.dto.request.BookingUpdateRequest;
import com.cagongu.tourbe.dto.response.BookingResponse;
import com.cagongu.tourbe.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {TourMapper.class, PromotionMapper.class})
public interface BookingMapper {
    @Mapping(target = "idOrder", ignore = true)
    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "dateEdited", ignore = true)
    @Mapping(target = "dateDeleted", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "tour", ignore = true)
    Booking bookingCreateRequestToBooking(BookingCreateRequest request);

    @Mapping(target = "tour", source = "tour")
    BookingResponse bookingToBookingResponse(Booking booking);

    @Mapping(target = "idOrder", ignore = true)
    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "dateEdited", ignore = true)
    @Mapping(target = "dateDeleted", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "tour", ignore = true)
    void updateBooking(@MappingTarget Booking booking, BookingUpdateRequest request);
}
