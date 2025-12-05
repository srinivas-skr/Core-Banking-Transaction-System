package com.corebanking.repository;

import com.corebanking.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Repository interface for Account operations.
 * Defines the contract for data access - implementations can use JDBC, JPA, etc.
 */
public interface AccountRepository {

    /**
     * Find account by email address.
     */
    Optional<Account> findByEmail(String email);

    /**
     * Find account by account number.
     */
    Optional<Account> findByAccountNumber(Long accountNumber);

    /**
     * Get balance for an account.
     */
    BigDecimal getBalance(Long accountNumber);

    /**
     * Update account balance.
     */
    void updateBalance(Long accountNumber, BigDecimal newBalance);

    /**
     * Validate credentials.
     */
    boolean validateCredentials(String email, int securityPin);
}
