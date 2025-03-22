package com.cagongu.tourbe.repository;


import com.cagongu.tourbe.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findAllByDescriptionIsLikeIgnoreCase(String name);
    List<Tour> findAllByTypeIsLikeIgnoreCase(String name);
    List<Tour> findAllByAddressIsLikeIgnoreCase(String name);

    @Query("SELECT t, " +
            "COALESCE(AVG(e.numberStar), 0) AS avgRating, " +
            "COUNT(e.idEvaluate) AS ratingCount " +
            "FROM Tour t " +
            "LEFT JOIN t.bookings b " +
            "LEFT JOIN b.evaluate e " +
            "GROUP BY t " +
            "ORDER BY avgRating DESC, ratingCount DESC")
    Page<Object[]> findToursSortedByAverageRatingAndCount(Pageable pageable);

}
