package com.corebanking.dto;

import java.math.BigDecimal;

/**
 * DTO for fund transfer request.
 */
public class TransferRequest {

    private final Long senderAccountNumber;
    private final Long receiverAccountNumber;
    private final BigDecimal amount;

    public TransferRequest(Long senderAccountNumber, Long receiverAccountNumber, BigDecimal amount) {
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
    }

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public Long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
