package com.cagongu.tourbe.repository;

import com.cagongu.tourbe.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findUserByEmail(String email);
    Optional<Account> findUserByUsername(String username);
}
