package com.corebanking.service.impl;

import com.corebanking.config.ConnectionManager;
import com.corebanking.dto.TransferRequest;
import com.corebanking.dto.TransferResult;
import com.corebanking.exception.AccountNotFoundException;
import com.corebanking.exception.InsufficientFundsException;
import com.corebanking.exception.TransferException;
import com.corebanking.model.Transaction;
import com.corebanking.model.TransactionType;
import com.corebanking.repository.AccountRepository;
import com.corebanking.repository.TransactionRepository;
import com.corebanking.repository.impl.AccountRepositoryImpl;
import com.corebanking.repository.impl.TransactionRepositoryImpl;
import com.corebanking.service.AccountService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of AccountService.
 * 
 * KEY FEATURE: ACID-Compliant Fund Transfers
 * - Atomicity: All or nothing (commit/rollback)
 * - Consistency: Balance validation before transfer
 * - Isolation: Transaction isolation via connection
 * - Durability: Committed data is persisted
 */
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
        this.transactionRepository = new TransactionRepositoryImpl();
    }

    // Constructor injection for testing
    public AccountServiceImpl(AccountRepository accountRepository, 
                               TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public BigDecimal getBalance(Long accountNumber) {
        return accountRepository.getBalance(accountNumber);
    }

    /**
     * ACID-Compliant Money Transfer.
     * 
     * This method demonstrates proper transaction management:
     * 1. Disable auto-commit (start transaction)
     * 2. Validate sender balance
     * 3. Debit sender account
     * 4. Credit receiver account
     * 5. Log both transactions
     * 6. Commit on success, rollback on failure
     */
    @Override
    public TransferResult transfer(TransferRequest request) {
        Connection conn = null;
        
        try {
            conn = ConnectionManager.getConnection();
            
            // STEP 1: Disable auto-commit (Start Transaction)
            conn.setAutoCommit(false);

            // STEP 2: Validate sender has sufficient funds
            BigDecimal senderBalance = getBalanceWithConnection(conn, request.getSenderAccountNumber());
            if (senderBalance == null) {
                throw new AccountNotFoundException(request.getSenderAccountNumber());
            }
            
            if (senderBalance.compareTo(request.getAmount()) < 0) {
                throw new InsufficientFundsException(
                        senderBalance.doubleValue(), 
                        request.getAmount().doubleValue()
                );
            }

            // STEP 3: Debit sender
            BigDecimal newSenderBalance = senderBalance.subtract(request.getAmount());
            updateBalanceWithConnection(conn, request.getSenderAccountNumber(), newSenderBalance);

            // STEP 4: Credit receiver
            BigDecimal receiverBalance = getBalanceWithConnection(conn, request.getReceiverAccountNumber());
            if (receiverBalance == null) {
                throw new AccountNotFoundException(request.getReceiverAccountNumber());
            }
            BigDecimal newReceiverBalance = receiverBalance.add(request.getAmount());
            updateBalanceWithConnection(conn, request.getReceiverAccountNumber(), newReceiverBalance);

            // STEP 5: Log transactions for both parties
            transactionRepository.logTransaction(
                    conn, 
                    request.getSenderAccountNumber(), 
                    TransactionType.TRANSFER_SENT, 
                    request.getAmount(), 
                    newSenderBalance
            );
            transactionRepository.logTransaction(
                    conn, 
                    request.getReceiverAccountNumber(), 
                    TransactionType.TRANSFER_RECEIVED, 
                    request.getAmount(), 
                    newReceiverBalance
            );

            // STEP 6: Commit (Durability - data is now persisted)
            conn.commit();
            
            return TransferResult.success(newSenderBalance);

        } catch (AccountNotFoundException | InsufficientFundsException e) {
            // Business exceptions - rollback and return failure
            rollbackQuietly(conn);
            return TransferResult.failure(e.getMessage());
            
        } catch (SQLException e) {
            // Database error - rollback and wrap
            rollbackQuietly(conn);
            throw new TransferException("Database error during transfer", e);
            
        } finally {
            // Always close the connection
            ConnectionManager.closeQuietly(conn);
        }
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountNumber) {
        return transactionRepository.getTransactionHistory(accountNumber);
    }

    // Helper: Get balance within existing transaction
    private BigDecimal getBalanceWithConnection(Connection conn, Long accountNumber) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        }
        return null;
    }

    // Helper: Update balance within existing transaction
    private void updateBalanceWithConnection(Connection conn, Long accountNumber, 
                                              BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setLong(2, accountNumber);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new AccountNotFoundException(accountNumber);
            }
        }
    }

    // Helper: Rollback quietly (suppress exceptions)
    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                // Log in production, suppress for now
            }
        }
    }
}
