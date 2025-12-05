package com.corebanking.exception;

/**
 * Thrown when an account is not found in the database.
 */
public class AccountNotFoundException extends BankingException {

    private final Long accountNumber;

    public AccountNotFoundException(Long accountNumber) {
        super("Account not found: " + accountNumber);
        this.accountNumber = accountNumber;
    }

    public AccountNotFoundException(String email) {
        super("Account not found for email: " + email);
        this.accountNumber = null;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}
