package com.corebanking.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Transaction entity representing a banking transaction.
 * Maps to the 'transactions' table in the database.
 */
public class Transaction {

    private Long transactionId;
    private Long accountNumber;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private Timestamp transactionDate;

    public Transaction() {
    }

    public Transaction(Long transactionId, Long accountNumber, TransactionType transactionType,
                       BigDecimal amount, BigDecimal balanceAfter, Timestamp transactionDate) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return String.format("%s | %-17s | Rs.%-10.2f | Balance: Rs.%.2f",
                transactionDate, transactionType.getDisplayName(), amount, balanceAfter);
    }
}
