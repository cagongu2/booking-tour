package com.cagongu.tourbe.controller;

import com.cagongu.tourbe.dto.request.BookingCreateRequest;
import com.cagongu.tourbe.dto.response.ApiResponse;
import com.cagongu.tourbe.dto.response.BookingResponse;
import com.cagongu.tourbe.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private static final String BOOKING_PATH = "/booking";
    private static final String BOOKING_PATH_ID = BOOKING_PATH + "/{idOrder}";

    private final BookingService bookingService;

    @GetMapping(BOOKING_PATH)
    public ApiResponse<List<BookingResponse>> getAll() {
        List<BookingResponse> responses = bookingService.listAll()
                .stream()
                .peek(bookingResponse -> {
                    if (bookingResponse.getTour() != null) {
                        bookingResponse.getTour().setDescription(null);
                    }
                })
                .toList();

        return ApiResponse.<List<BookingResponse>>builder()
                .result(responses)
                .build();
    }


    @PostMapping(BOOKING_PATH)
    public ApiResponse<BookingResponse> createBooking(@RequestParam Long userId,
                                                      @RequestParam Long tourId,
                                                      @RequestBody BookingCreateRequest request){
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.create(tourId, userId, request))
                .build();
    }

    @DeleteMapping(BOOKING_PATH_ID)
    public ApiResponse<ResponseEntity<Void>> deleteBooking(@PathVariable Long idOrder){
        bookingService.delete(idOrder);
        return ApiResponse.<ResponseEntity<Void>>builder()
                .message("Booking deleted successfully.")
                .result(ResponseEntity.noContent().build())
                .build();
    }

}
