package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.EvaluateRequest;
import com.cagongu.tourbe.dto.response.BookingResponse;
import com.cagongu.tourbe.dto.response.EvaluateResponse;
import com.cagongu.tourbe.mapper.BookingMapper;
import com.cagongu.tourbe.mapper.EvaluateMapper;
import com.cagongu.tourbe.model.Booking;
import com.cagongu.tourbe.model.Evaluate;
import com.cagongu.tourbe.repository.AccountRepository;
import com.cagongu.tourbe.repository.BookingRepository;
import com.cagongu.tourbe.repository.EvaluateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluateServiceImpl implements EvaluateService {
    private final EvaluateRepository evaluateRepository;
    private final BookingRepository bookingRepository;
    private final AccountRepository accountRepository;
    private final EvaluateMapper mapper;
    private final BookingMapper bookingMapper;

    @Override
    public EvaluateResponse createEvaluate(EvaluateRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow();

        Evaluate evaluate = Evaluate.builder()
                .account(accountRepository.findById(request.getAccountId()).orElse(null))
                .booking(booking)
                .content(request.getContent())
                .title(request.getTitle())
                .numberStar(request.getNumberStar())
                .build();

        evaluateRepository.save(evaluate);

        booking.setEvaluate(evaluate);
        bookingRepository.save(booking);

        return mapper.evaluateToResponse(evaluate);
    }

    @Override
    public List<EvaluateResponse> getAllEvaluate() {
        return evaluateRepository.findAll().stream().map(mapper::evaluateToResponse).toList();
    }

    @Override
    public List<EvaluateResponse> getAllByTourId(Long tourId) {
        List<BookingResponse> bookingResponseList = bookingRepository.findAll().stream()
                .filter(booking -> booking.getTour().getIdTour().equals(tourId))
                .map(bookingMapper::bookingToBookingResponse)
                .toList();

        List<EvaluateResponse> evaluateResponseList = new ArrayList<>();

        for(var booking : bookingResponseList){
            evaluateResponseList.add(mapper.evaluateToResponse(booking.getEvaluate()));
        }

        return evaluateResponseList;
    }
}
