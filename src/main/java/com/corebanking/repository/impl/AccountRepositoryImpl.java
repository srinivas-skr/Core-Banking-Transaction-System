package com.corebanking.repository.impl;

import com.corebanking.config.ConnectionManager;
import com.corebanking.model.Account;
import com.corebanking.repository.AccountRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * JDBC implementation of AccountRepository.
 * Uses PreparedStatements to prevent SQL injection.
 */
public class AccountRepositoryImpl implements AccountRepository {

    private static final String FIND_BY_EMAIL = 
        "SELECT account_number, full_name, email, balance, security_pin FROM accounts WHERE email = ?";
    
    private static final String FIND_BY_ACCOUNT_NUMBER = 
        "SELECT account_number, full_name, email, balance, security_pin FROM accounts WHERE account_number = ?";
    
    private static final String VALIDATE_CREDENTIALS = 
        "SELECT 1 FROM accounts WHERE email = ? AND security_pin = ?";
    
    private static final String GET_BALANCE = 
        "SELECT balance FROM accounts WHERE account_number = ?";
    
    private static final String UPDATE_BALANCE = 
        "UPDATE accounts SET balance = ? WHERE account_number = ?";

    @Override
    public Optional<Account> findByEmail(String email) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_EMAIL)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while finding account by email", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> findByAccountNumber(Long accountNumber) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(FIND_BY_ACCOUNT_NUMBER)) {
            
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while finding account by number", e);
        }
        return Optional.empty();
    }

    @Override
    public BigDecimal getBalance(Long accountNumber) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_BALANCE)) {
            
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error while getting balance", e);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void updateBalance(Long accountNumber, BigDecimal newBalance) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_BALANCE)) {
            
            ps.setBigDecimal(1, newBalance);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while updating balance", e);
        }
    }

    @Override
    public boolean validateCredentials(String email, int securityPin) {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(VALIDATE_CREDENTIALS)) {
            
            ps.setString(1, email);
            ps.setInt(2, securityPin);
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while validating credentials", e);
        }
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountNumber(rs.getLong("account_number"));
        account.setFullName(rs.getString("full_name"));
        account.setEmail(rs.getString("email"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setSecurityPin(rs.getInt("security_pin"));
        return account;
    }
}
