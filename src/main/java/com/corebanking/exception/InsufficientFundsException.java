package com.corebanking.exception;

/**
 * Thrown when account balance is insufficient for a transaction.
 */
public class InsufficientFundsException extends BankingException {

    private final double availableBalance;
    private final double requestedAmount;

    public InsufficientFundsException(double availableBalance, double requestedAmount) {
        super(String.format("Insufficient funds. Available: Rs.%.2f, Requested: Rs.%.2f",
                availableBalance, requestedAmount));
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }
}
