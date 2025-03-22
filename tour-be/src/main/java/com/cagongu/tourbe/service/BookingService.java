package com.cagongu.tourbe.service;


import com.cagongu.tourbe.dto.request.BookingCreateRequest;
import com.cagongu.tourbe.dto.request.UserUpdateRequest;
import com.cagongu.tourbe.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    List<BookingResponse> listAll();

    BookingResponse getById(Long id);
    BookingResponse create(Long tourId, Long userId, BookingCreateRequest request);
    BookingResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);
}
