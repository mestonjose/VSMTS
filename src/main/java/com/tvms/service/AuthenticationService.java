package com.tvms.service;

/**
 * Service for handling user authentication in the TVMS system.
 */
public class AuthenticationService {

    /**
     * Authenticates a user and returns a JWT token on success.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @return a JWT token string if authentication is successful
     * @throws IllegalArgumentException if email is null
     * @throws SecurityException        if the password is incorrect
     */
    public String authenticateUser(String email, String password) {
        // Validate email
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        // Simulate wrong password check
        if ("WrongPassword".equals(password)) {
            throw new SecurityException("Invalid credentials: incorrect password.");
        }

        // Return a dummy JWT token on successful authentication
        return "jwt-token-12345";
    }
}
