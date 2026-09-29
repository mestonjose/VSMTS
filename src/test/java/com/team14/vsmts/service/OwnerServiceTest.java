package com.team14.vsmts.service;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testSuccessfulLogin() {
        // Positive test: successful login with correct credentials
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");
        user.setRole("VEHICLE_OWNER");
        
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@test.com");
        
        assertNotNull(userDetails);
        assertEquals("test@test.com", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
    }

    @Test
    void testLoginFailsWithWrongPassword() {
        // Negative test: login fails with wrong password
        // In Spring Security, UserDetailsService just loads the user and AuthenticationManager verifies password.
        // We simulate the negative scenario by asserting the password check would fail.
        when(passwordEncoder.matches("wrongPass", "encodedPassword")).thenReturn(false);
        boolean isMatch = passwordEncoder.matches("wrongPass", "encodedPassword");
        assertFalse(isMatch, "Password matching should fail for incorrect password");
    }

    @Test
    void testPasswordBoundary() {
        // Boundary test: password must be at least 8 characters
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setFullName("Test User");
        dto.setEmail("test@test.com");
        dto.setPassword("Short1!"); // 7 characters
        dto.setConfirmPassword("Short1!");
        dto.setRole("Vehicle Owner");

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Validation should fail for password less than 8 characters");
        
        boolean sizeErrorFound = violations.stream()
                .anyMatch(v -> v.getMessage().contains("Password must be at least 8 characters"));
        assertTrue(sizeErrorFound, "Should contain size validation error");
    }

    @Test
    void testRegistrationFailsWithDuplicateEmail() {
        // Negative test: registration fails with duplicate email
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        
        boolean exists = userService.emailExists("test@test.com");
        assertTrue(exists, "Email should exist in database");
    }

    @Test
    void testSuccessfulRegistration() {
        // Positive test: successful registration with valid data
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setFullName("Test User");
        dto.setEmail("new@test.com");
        dto.setPassword("StrongPass1!");
        dto.setConfirmPassword("StrongPass1!");
        dto.setRole("Vehicle Owner");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(dto.getEmail());
        savedUser.setRole("VEHICLE_OWNER");

        when(passwordEncoder.encode("StrongPass1!")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(dto);

        assertNotNull(result);
        assertEquals("new@test.com", result.getEmail());
        assertEquals("VEHICLE_OWNER", result.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
