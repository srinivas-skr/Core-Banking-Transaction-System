package com.corebanking.repository.impl;

import com.corebanking.config.ConnectionManager;
import com.corebanking.model.Transaction;
import com.corebanking.model.TransactionType;
import com.corebanking.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of TransactionRepository.
 */
public class TransactionRepositoryImpl implements TransactionRepository {

    // Oracle 11g compatible - uses subquery for ID generation
    private static final String INSERT_TRANSACTION = 
        "INSERT INTO transactions (transaction_id, account_number, transaction_type, amount, balance_after) " +
        "VALUES ((SELECT NVL(MAX(transaction_id), 0) + 1 FROM transactions), ?, ?, ?, ?)";

    private static final String GET_HISTORY = 
        "SELECT transaction_id, account_number, transaction_type, amount, balance_after, transaction_date " +
        "FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";

    private static final String GET_HISTORY_PAGINATED = 
        "SELECT * FROM (SELECT t.*, ROWNUM rn FROM (" +
        "SELECT transaction_id, account_number, transaction_type, amount, balance_after, transaction_date " +
        "FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC" +
        ") t WHERE ROWNUM <= ?) WHERE rn > ?";

    @Override
    public void logTransaction(Connection conn, Long accountNumber, TransactionType type,
                               BigDecimal amount, BigDecimal balanceAfter) {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_TRANSACTION)) {
            ps.setLong(1, accountNumber);
            ps.setString(2, type.getDisplayName());
            ps.setBigDecimal(3, amount);
            ps.setBigDecimal(4, balanceAfter);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to log transaction", e);
        }
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_HISTORY)) {
            
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching transaction history", e);
        }
        
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountNumber, int limit, int offset) {
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_HISTORY_PAGINATED)) {
            
            ps.setLong(1, accountNumber);
            ps.setInt(2, offset + limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching paginated history", e);
        }
        
        return transactions;
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getLong("transaction_id"));
        transaction.setAccountNumber(rs.getLong("account_number"));
        transaction.setTransactionType(TransactionType.fromString(rs.getString("transaction_type")));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setBalanceAfter(rs.getBigDecimal("balance_after"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
        return transaction;
    }
}
