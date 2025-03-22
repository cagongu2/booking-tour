package com.cagongu.tourbe.repository;

import com.cagongu.tourbe.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<ForgotPassword, String> {
}
