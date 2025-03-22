package com.cagongu.tourbe.repository;

import com.cagongu.tourbe.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
}
