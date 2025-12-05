package com.corebanking.service;

import com.corebanking.dto.TransferRequest;
import com.corebanking.dto.TransferResult;
import com.corebanking.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for account operations.
 */
public interface AccountService {

    /**
     * Get current balance for an account.
     *
     * @param accountNumber Account number
     * @return Current balance
     */
    BigDecimal getBalance(Long accountNumber);

    /**
     * Transfer money between accounts.
     * This operation is ACID-compliant.
     *
     * @param request Transfer details
     * @return TransferResult with success/failure info
     */
    TransferResult transfer(TransferRequest request);

    /**
     * Get transaction history for an account.
     *
     * @param accountNumber Account number
     * @return List of transactions, ordered by date descending
     */
    List<Transaction> getTransactionHistory(Long accountNumber);
}
