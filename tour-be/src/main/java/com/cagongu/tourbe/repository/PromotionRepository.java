package com.cagongu.tourbe.repository;

import com.cagongu.tourbe.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Promotion findByCode(String code);
}
