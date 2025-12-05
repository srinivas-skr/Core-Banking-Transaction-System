package com.corebanking.dto;

/**
 * DTO for login request.
 * Never expose entity objects directly - always use DTOs.
 */
public class LoginRequest {

    private final String email;
    private final int securityPin;

    public LoginRequest(String email, int securityPin) {
        this.email = email;
        this.securityPin = securityPin;
    }

    public String getEmail() {
        return email;
    }

    public int getSecurityPin() {
        return securityPin;
    }
}
