package com.corebanking.repository;

import com.corebanking.model.Transaction;
import com.corebanking.model.TransactionType;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * Repository interface for Transaction operations.
 */
public interface TransactionRepository {

    /**
     * Log a transaction (uses existing connection for ACID compliance).
     */
    void logTransaction(Connection conn, Long accountNumber, TransactionType type,
                        BigDecimal amount, BigDecimal balanceAfter);

    /**
     * Get transaction history for an account.
     */
    List<Transaction> getTransactionHistory(Long accountNumber);

    /**
     * Get transaction history with pagination.
     */
    List<Transaction> getTransactionHistory(Long accountNumber, int limit, int offset);
}
