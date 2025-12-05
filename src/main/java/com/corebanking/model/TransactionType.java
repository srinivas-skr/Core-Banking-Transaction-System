package com.corebanking.model;

/**
 * Enumeration of transaction types.
 * Provides type-safety instead of raw strings.
 */
public enum TransactionType {

    TRANSFER_SENT("Transfer Sent"),
    TRANSFER_RECEIVED("Transfer Received"),
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parse database string to enum.
     */
    public static TransactionType fromString(String value) {
        for (TransactionType type : values()) {
            if (type.displayName.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown transaction type: " + value);
    }
}
