package com.corebanking.exception;

/**
 * Thrown when authentication fails.
 */
public class AuthenticationException extends BankingException {

    public AuthenticationException(String message) {
        super(message);
    }
}
