package com.cagongu.tourbe.repository;


import com.cagongu.tourbe.model.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Long> {
}
