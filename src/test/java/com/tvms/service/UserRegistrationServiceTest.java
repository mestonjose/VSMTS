package com.tvms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link UserRegistrationService}.
 */
class UserRegistrationServiceTest {

    private UserRegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new UserRegistrationService();
    }

    @Test
    void testValidUserRegistration() {
        boolean result = registrationService.registerUser("newuser@tvms.com", "SecurePass123", "John Doe");
        assertTrue(result, "Valid registration should return true.");
    }

    @Test
    void testEmptyPasswordThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.registerUser("user@tvms.com", "", "Jane Doe"),
                "Empty password should throw IllegalArgumentException."
        );
    }

    @Test
    void testDuplicateEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.registerUser("existing@tvms.com", "SomePass456", "Duplicate User"),
                "Duplicate email should throw IllegalArgumentException."
        );
    }
}
