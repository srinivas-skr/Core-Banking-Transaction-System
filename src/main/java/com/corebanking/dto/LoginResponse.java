package com.corebanking.dto;

import java.math.BigDecimal;

/**
 * DTO for login response.
 * Contains only necessary user info - never exposes sensitive data like PIN.
 */
public class LoginResponse {

    private final Long accountNumber;
    private final String fullName;
    private final String email;
    private final BigDecimal balance;

    public LoginResponse(Long accountNumber, String fullName, String email, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.email = email;
        this.balance = balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("Welcome, %s! (Account: %d)", fullName, accountNumber);
    }
}
