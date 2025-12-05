package com.corebanking.exception;

/**
 * Thrown when a fund transfer fails.
 */
public class TransferException extends BankingException {

    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
