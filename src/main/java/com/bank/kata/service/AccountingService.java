package com.bank.kata.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.kata.entity.Account;
import com.bank.kata.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountingService {

    private final AccountRepository accountRepository;

    public AccountingService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /** ✅ Créer un compte */
    @Transactional
    public Account createAccount(String accountID) {
        if (accountRepository.findByAccountID(accountID).isPresent()) {
            throw new IllegalStateException("ACCOUNT_EXISTS: Ce compte existe déjà.");
        }
        Account newAccount = new Account(accountID, 0.0);
        return accountRepository.save(newAccount);
    }

    /** ✅ Recharger un compte */
    @Transactional
    public Account topUp(String accountID, double amount) {
        Account account = accountRepository.findByAccountID(accountID)
                .orElseThrow(() -> new IllegalStateException("ACCOUNT_DOES_NOT_EXIST"));

        if (amount <= 0) {
            throw new IllegalArgumentException("INVALID_AMOUNT: Le montant doit être positif.");
        }

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    /** ✅ Obtenir le solde */
    public double getBalance(String accountID) {
        Account account = accountRepository.findByAccountID(accountID)
                .orElseThrow(() -> new IllegalStateException("ACCOUNT_DOES_NOT_EXIST"));
        return account.getBalance();
    }
}

