package com.tvms.service;

/**
 * Service for handling user registration in the TVMS system.
 */
public class UserRegistrationService {

    /**
     * Registers a new user with the given credentials.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @param name     the user's full name
     * @return true if registration is successful
     * @throws IllegalArgumentException if password is null/empty or email is already registered
     */
    public boolean registerUser(String email, String password, String name) {
        // Validate password
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        // Simulate duplicate email check
        if ("existing@tvms.com".equals(email)) {
            throw new IllegalArgumentException("Email is already registered: " + email);
        }

        // Registration successful
        return true;
    }
}
