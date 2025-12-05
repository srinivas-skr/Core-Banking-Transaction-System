package com.corebanking.dto;

import java.math.BigDecimal;

/**
 * DTO for fund transfer response.
 */
public class TransferResult {

    private final boolean success;
    private final String message;
    private final BigDecimal newBalance;

    private TransferResult(boolean success, String message, BigDecimal newBalance) {
        this.success = success;
        this.message = message;
        this.newBalance = newBalance;
    }

    public static TransferResult success(BigDecimal newBalance) {
        return new TransferResult(true, "Transfer completed successfully", newBalance);
    }

    public static TransferResult failure(String reason) {
        return new TransferResult(false, reason, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }
}
