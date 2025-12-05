package com.corebanking.service.impl;

import com.corebanking.dto.LoginRequest;
import com.corebanking.dto.LoginResponse;
import com.corebanking.exception.AuthenticationException;
import com.corebanking.model.Account;
import com.corebanking.repository.AccountRepository;
import com.corebanking.repository.impl.AccountRepositoryImpl;
import com.corebanking.service.AuthenticationService;

/**
 * Implementation of AuthenticationService.
 * Handles user authentication with SQL injection prevention.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;

    public AuthenticationServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    // Constructor injection for testing
    public AuthenticationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        // Validate credentials first
        if (!validateCredentials(request.getEmail(), request.getSecurityPin())) {
            throw new AuthenticationException("Invalid email or PIN");
        }

        // Fetch account details
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("Account not found"));

        // Return safe DTO (without sensitive PIN)
        return new LoginResponse(
                account.getAccountNumber(),
                account.getFullName(),
                account.getEmail(),
                account.getBalance()
        );
    }

    @Override
    public boolean validateCredentials(String email, int pin) {
        return accountRepository.validateCredentials(email, pin);
    }
}
