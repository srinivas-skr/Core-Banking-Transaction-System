package com.corebanking.service;

import com.corebanking.dto.LoginRequest;
import com.corebanking.dto.LoginResponse;

/**
 * Service interface for authentication operations.
 */
public interface AuthenticationService {

    /**
     * Authenticate user and return session info.
     *
     * @param request Login credentials
     * @return LoginResponse with user details
     * @throws com.corebanking.exception.AuthenticationException if credentials are invalid
     */
    LoginResponse authenticate(LoginRequest request);

    /**
     * Validate if credentials are correct.
     *
     * @param email User's email
     * @param pin Security PIN
     * @return true if valid
     */
    boolean validateCredentials(String email, int pin);
}
