package com.tvms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AuthenticationService}.
 */
class AuthenticationServiceTest {

    private AuthenticationService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthenticationService();
    }

    @Test
    void testSuccessfulLogin() {
        String token = authService.authenticateUser("user@tvms.com", "CorrectPassword");
        assertNotNull(token, "Successful login should return a non-null JWT token.");
    }

    @Test
    void testInvalidPasswordLogin() {
        assertThrows(SecurityException.class, () ->
                authService.authenticateUser("user@tvms.com", "WrongPassword"),
                "Wrong password should throw SecurityException."
        );
    }

    @Test
    void testNullUsernameLogin() {
        assertThrows(IllegalArgumentException.class, () ->
                authService.authenticateUser(null, "SomePassword"),
                "Null email should throw IllegalArgumentException."
        );
    }
}
