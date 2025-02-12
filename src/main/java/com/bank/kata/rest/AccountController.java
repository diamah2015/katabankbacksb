package com.bank.kata.rest;

import org.springframework.web.bind.annotation.RestController;

import com.bank.kata.entity.Account;
import com.bank.kata.service.AccountingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*") // Permet l'accès depuis Angular
public class AccountController {

    private final AccountingService accountService;

    public AccountController(AccountingService accountService) {
        this.accountService = accountService;
    }

    /** ✅ Endpoint pour créer un compte */
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> request) {
        try {
            Account account = accountService.createAccount(request.get("accountID"));
            return ResponseEntity.ok(Map.of("message", "Compte créé avec succès.", "account", account));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** ✅ Endpoint pour recharger un compte */
    @PostMapping("/topup")
    public ResponseEntity<?> topUpAccount(@RequestBody Map<String, Object> request) {
        try {
            String accountID = (String) request.get("accountID");
            double amount = ((Number) request.get("amount")).doubleValue();
            Account updatedAccount = accountService.topUp(accountID, amount);
            return ResponseEntity.ok(Map.of("message", "Compte rechargé avec succès.", "balance", updatedAccount.getBalance()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** ✅ Endpoint pour obtenir le solde */
    @GetMapping("/balance/{accountID}")
    public ResponseEntity<?> getBalance(@PathVariable String accountID) {
        try {
            double balance = accountService.getBalance(accountID);
            return ResponseEntity.ok(Map.of("accountID", accountID, "balance", balance));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    
}

