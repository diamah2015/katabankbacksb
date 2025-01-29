package com.bank.kata.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.kata.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountID(String accountID);
}
