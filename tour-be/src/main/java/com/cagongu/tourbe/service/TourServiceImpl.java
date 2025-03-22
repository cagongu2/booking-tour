package com.cagongu.tourbe.service;


import com.cagongu.tourbe.dto.request.TourCreationRequest;
import com.cagongu.tourbe.dto.request.TourUpdateRequest;
import com.cagongu.tourbe.dto.response.TourRatingResponse;
import com.cagongu.tourbe.dto.response.TourResponse;
import com.cagongu.tourbe.enums.StatusAction;
import com.cagongu.tourbe.exception.AppException;
import com.cagongu.tourbe.exception.ErrorCode;
import com.cagongu.tourbe.mapper.TourMapper;
import com.cagongu.tourbe.model.Account;
import com.cagongu.tourbe.model.Favorite;
import com.cagongu.tourbe.model.Tour;
import com.cagongu.tourbe.repository.AccountRepository;
import com.cagongu.tourbe.repository.BookingRepository;
import com.cagongu.tourbe.repository.FavoriteRepository;
import com.cagongu.tourbe.repository.TourRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    private final FavoriteRepository favoriteRepository;
    private final AccountRepository accountRepository;
    private final BookingRepository bookingRepository;
    private final CacheManager cacheManager;


    @Cacheable(cacheNames = "tourListCache")
    @Override
    public List<TourResponse> listAll(String description, String type, String place) {
        log.info("Fetching all tours");

        if(StringUtils.hasText(description))
            return listAllByDescription(description);
        else if(StringUtils.hasText(type))
            return listAllByType(type);
        else if(StringUtils.hasText(place))
            return listAllByPlace(place);
        else {
            List<Tour> tours = tourRepository.findAll();
            return tours.stream()
                    .map(tourMapper::tourToTourResponse)
                     .filter(tour -> tour.getStatus() != StatusAction.DELETE)
                    .toList();
        }
    }

    public List<TourResponse> listAllByDescription(String name)  {
        List<Tour> tours = tourRepository.findAllByDescriptionIsLikeIgnoreCase("%" + name + "%");

        return tours.stream()
                .map(tourMapper::tourToTourResponse)
                 .filter(tour -> tour.getStatus() != StatusAction.DELETE)
                .toList();
    }

    public List<TourResponse> listAllByType(String name) {
        List<Tour> tours = tourRepository.findAllByTypeIsLikeIgnoreCase("%" + name + "%");

        return tours.stream()
                .map(tourMapper::tourToTourResponse)
                 .filter(tour -> tour.getStatus() != StatusAction.DELETE)
                .toList();
    }

    public List<TourResponse> listAllByPlace(String name) {
        List<Tour> tours = tourRepository.findAllByAddressIsLikeIgnoreCase("%" + name + "%");

        return tours.stream()
                .map(tourMapper::tourToTourResponse)
                 .filter(tour -> tour.getStatus() != StatusAction.DELETE)
                .toList();
    }

    private void clearCache(Long tourId) {
        cacheManager.getCache("tourCache").evict(tourId);
        cacheManager.getCache("tourListCache").clear();
        if (cacheManager.getCache("tourCache") != null ){
            cacheManager.getCache("tourCache").evict(tourId);
        }

        if (cacheManager.getCache("tourListCache") != null) {
            cacheManager.getCache("tourListCache").clear();
        }
    }

    @Cacheable(cacheNames = "tourCache", key = "#id")
    @Override
    public TourResponse getById(Long id) {
        log.info("Fetching tour with ID: {}", id);
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));
        return tourMapper.tourToTourResponse(tour);
    }

    @Override
    @Transactional
    public TourResponse create(TourCreationRequest request) {
        log.info("Creating new tour");

        if (cacheManager.getCache("tourListCache") != null) {
            cacheManager.getCache("tourListCache").clear();
        }

        Tour tour = tourMapper.tourCreateRequestToTour(request);
        return tourMapper.tourToTourResponse(tourRepository.save(tour));
    }

    @Override
    @Transactional
    @CachePut(value = "tourListCache", key = "#id")
    public TourResponse update(Long id, TourUpdateRequest request) {
        log.info("Updating tour with ID: {}", id);

        clearCache(id);

        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        tourMapper.updateTour(tour, request);
        return tourMapper.tourToTourResponse(tourRepository.save(tour));
    }

    @Override
    public TourResponse addFavorite(Long userId, Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Favorite favorite = Favorite.builder()
                .account(account)
                .statusAction(StatusAction.ACTIVE.name())
                .build();

        tour.getFavorites().add(favorite);

        tourRepository.save(tour);

        return tourMapper.tourToTourResponse(tour);
    }

    @Override
    public TourResponse removeFavorite(Long userId, Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));


        tour.getFavorites().forEach(favorite -> {
            if(favorite.getAccount().getIdAccount().equals(userId)){
                favorite.setStatusAction(StatusAction.DELETE.name());
            }
        });

        tourRepository.save(tour);

        return tourMapper.tourToTourResponse(tour);
    }


    @Override
    @Transactional
    @CacheEvict(cacheNames = "tourCache", key = "#id")
    public TourResponse delete(Long id) {
        log.info("Deleting tour with ID: {}", id);
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        tour.setStatus(StatusAction.DELETE);

        return  tourMapper.tourToTourResponse(tourRepository.save(tour));
//        tourRepository.deleteById(tour.getIdTour());
    }
    @Override
    public Page<TourRatingResponse> getToursSortedByRating(Pageable pageable) {
        // Lấy danh sách tours đã được sắp xếp và phân trang
        Page<Object[]> result = tourRepository.findToursSortedByAverageRatingAndCount(pageable);

        // Ánh xạ kết quả từ query sang DTO
        return result.map(row -> new TourRatingResponse(
                ((Tour) row[0]).getIdTour(),
                ((Tour) row[0]).getImage(),  // Lấy ảnh từ Tour
                ((Tour) row[0]).getTitleTour(),
                (Double) row[1],
                (Long) row[2]
        ));
    }

    @Override
    public TourRatingResponse getTourRatingResponseById(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_FOUND));

        Double avgRating = tour.getBookings().stream()
                .mapToDouble(booking -> booking.getEvaluate().getNumberStar())
                .average()
                .orElse(0.0);

        TourRatingResponse tourRatingResponse;
        tourRatingResponse = TourRatingResponse.builder()
                .avgRating(avgRating)
                .idTour(tour.getIdTour())
                .build();

        return tourRatingResponse;
    }
}
