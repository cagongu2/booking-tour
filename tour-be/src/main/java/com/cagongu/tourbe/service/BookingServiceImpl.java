package com.cagongu.tourbe.service;


import com.cagongu.tourbe.dto.request.BookingCreateRequest;
import com.cagongu.tourbe.dto.request.UserUpdateRequest;
import com.cagongu.tourbe.dto.response.BookingResponse;
import com.cagongu.tourbe.exception.AppException;
import com.cagongu.tourbe.exception.ErrorCode;
import com.cagongu.tourbe.mapper.BookingMapper;
import com.cagongu.tourbe.model.*;
import com.cagongu.tourbe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final TourRepository tourRepository;
    private final PromotionRepository promotionRepository;
    private final AccountRepository accountRepository;
    private final EvaluateRepository evaluateRepository;
    private final CustomerInfoRepository customerInfoRepository;

    @Override
    public List<BookingResponse> listAll() {
        return bookingRepository.findAll().stream().map(bookingMapper::bookingToBookingResponse).toList();
    }

    @Override
    public BookingResponse getById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(RuntimeException::new);

        return bookingMapper.bookingToBookingResponse(booking);
    }

    @Override
    public BookingResponse create(@RequestParam Long tourId, @RequestParam Long userId, @RequestBody BookingCreateRequest request) {
        // Kiểm tra xem tour có tồn tại không
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        // Kiểm tra xem tài khoản có tồn tại không
        Account account = accountRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        int total = Integer.parseInt(request.getTotal());

        int tmp = 0;
        if (account.getPayed() != null) {
            tmp = Integer.parseInt(account.getPayed());
        }
        tmp += total;

        account.setPayed(tmp + "");
        accountRepository.save(account);

        Promotion promotion = null;

        if (request.getPromotion() != null && request.getPromotion().getId() != null) {
            promotion = promotionRepository.findById(request.getPromotion().getId()).orElse(null);
        }

        if (promotion != null) {
            promotion.setQualityOnHand(promotion.getQualityOnHand() - 1);

            promotionRepository.save(promotion);

            request.setPromotion(null);
        }

        // Chuyển đổi từ request thành đối tượng Booking
        Booking booking = bookingMapper.bookingCreateRequestToBooking(request);

        booking.setAccount(account);
        booking.setTour(tour);
        booking.setPromotion(promotion);

        tour.getBookings().add(booking);

        tourRepository.save(tour);

        return bookingMapper.bookingToBookingResponse(booking);
    }

    @Override
    public BookingResponse update(Long id, UserUpdateRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();

        Evaluate evaluate = evaluateRepository.findAll().stream().filter(eval -> eval.getBooking() == booking).findFirst().orElse(null);
        if (evaluate != null) {
            evaluateRepository.delete(evaluate);
        }

        booking.setAccount(null);

        Tour tour = booking.getTour();
        if (tour != null) {
            tour.getBookings().remove(booking);
            tourRepository.save(tour);
        }
        booking.setTour(null);


        Set<CustomerInfo> customerInfos = booking.getCustomerInfoList();
        booking.getCustomerInfoList().removeAll(customerInfos);

        if (customerInfos != null && !customerInfos.isEmpty()) {
            customerInfoRepository.deleteAll(customerInfos);
        }

        bookingRepository.delete(booking);
    }
}
