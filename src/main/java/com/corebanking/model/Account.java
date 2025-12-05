package com.corebanking.model;

import java.math.BigDecimal;

/**
 * Account entity representing a bank account.
 * Maps to the 'accounts' table in the database.
 */
public class Account {

    private Long accountNumber;
    private String fullName;
    private String email;
    private BigDecimal balance;
    private Integer securityPin;

    public Account() {
    }

    public Account(Long accountNumber, String fullName, String email, BigDecimal balance, Integer securityPin) {
        this.accountNumber = accountNumber;
        this.fullName = fullName;
        this.email = email;
        this.balance = balance;
        this.securityPin = securityPin;
    }

    // Getters and Setters

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(Integer securityPin) {
        this.securityPin = securityPin;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
