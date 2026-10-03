package com.team14.vsmts.service;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDto validDto;
    private User savedUser;

    @BeforeEach
    void setUp() {
        validDto = new UserRegistrationDto();
        validDto.setFullName("Meston Jose S");
        validDto.setEmail("meston@example.com");
        validDto.setPassword("Test@1234");
        validDto.setConfirmPassword("Test@1234");
        validDto.setRole("Vehicle Owner");

        savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFullName("Meston Jose S");
        savedUser.setEmail("meston@example.com");
        savedUser.setPassword("$2a$10$encodedPasswordHash");
        savedUser.setRole("VEHICLE_OWNER");
    }

    // ==========================================
    // VSMTS-1: User Registration Tests
    // ==========================================
    @Nested
    @DisplayName("VSMTS-1: User Registration")
    class RegistrationTests {

        @Test
        @DisplayName("Positive: Should register a new Vehicle Owner successfully")
        void registerUser_VehicleOwner_ShouldSaveCorrectly() {
            when(passwordEncoder.encode("Test@1234")).thenReturn("$2a$10$encodedPasswordHash");
            when(userRepository.save(any(User.class))).thenReturn(savedUser);

            User result = userService.registerUser(validDto);

            assertNotNull(result);
            assertEquals("Meston Jose S", result.getFullName());
            assertEquals("meston@example.com", result.getEmail());
            assertEquals("VEHICLE_OWNER", result.getRole());
            verify(passwordEncoder, times(1)).encode("Test@1234");
            verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        @DisplayName("Positive: Should register a Service Center with correct role mapping")
        void registerUser_ServiceCenter_ShouldMapRoleCorrectly() {
            validDto.setRole("Service Center");
            User scUser = new User();
            scUser.setRole("SERVICE_CENTER");
            when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hash");
            when(userRepository.save(any(User.class))).thenReturn(scUser);

            User result = userService.registerUser(validDto);

            assertEquals("SERVICE_CENTER", result.getRole());
        }

        @Test
        @DisplayName("Positive: Should register an Administrator with correct role mapping")
        void registerUser_Admin_ShouldMapRoleCorrectly() {
            validDto.setRole("Administrator");
            User adminUser = new User();
            adminUser.setRole("ADMIN");
            when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hash");
            when(userRepository.save(any(User.class))).thenReturn(adminUser);

            User result = userService.registerUser(validDto);

            assertEquals("ADMIN", result.getRole());
        }

        @Test
        @DisplayName("Positive: Password should be encrypted before saving")
        void registerUser_ShouldEncryptPassword() {
            when(passwordEncoder.encode("Test@1234")).thenReturn("$2a$10$encodedPasswordHash");
            when(userRepository.save(any(User.class))).thenReturn(savedUser);

            userService.registerUser(validDto);

            verify(passwordEncoder).encode("Test@1234");
            assertEquals("$2a$10$encodedPasswordHash", savedUser.getPassword());
        }

        @Test
        @DisplayName("Negative: Should detect duplicate email")
        void emailExists_WhenDuplicate_ShouldReturnTrue() {
            when(userRepository.existsByEmail("meston@example.com")).thenReturn(true);

            boolean exists = userService.emailExists("meston@example.com");

            assertTrue(exists);
        }

        @Test
        @DisplayName("Negative: Should return false for non-existing email")
        void emailExists_WhenNotDuplicate_ShouldReturnFalse() {
            when(userRepository.existsByEmail("new@example.com")).thenReturn(false);

            boolean exists = userService.emailExists("new@example.com");

            assertFalse(exists);
        }

        @Test
        @DisplayName("Boundary: Should handle empty string email check")
        void emailExists_EmptyString_ShouldReturnFalse() {
            when(userRepository.existsByEmail("")).thenReturn(false);

            boolean exists = userService.emailExists("");

            assertFalse(exists);
        }
    }

    // ==========================================
    // VSMTS-2: User Login & Lookup Tests
    // ==========================================
    @Nested
    @DisplayName("VSMTS-2: User Login & Lookup")
    class LoginTests {

        @Test
        @DisplayName("Positive: Should find user by valid email")
        void findByEmail_ValidEmail_ShouldReturnUser() {
            when(userRepository.findByEmail("meston@example.com")).thenReturn(Optional.of(savedUser));

            User result = userService.findByEmail("meston@example.com");

            assertNotNull(result);
            assertEquals("meston@example.com", result.getEmail());
            assertEquals("Meston Jose S", result.getFullName());
        }

        @Test
        @DisplayName("Negative: Should return null for non-existing email")
        void findByEmail_InvalidEmail_ShouldReturnNull() {
            when(userRepository.findByEmail("nobody@example.com")).thenReturn(Optional.empty());

            User result = userService.findByEmail("nobody@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("Positive: Should return total user count")
        void countAll_ShouldReturnTotalCount() {
            when(userRepository.count()).thenReturn(15L);

            long count = userService.countAll();

            assertEquals(15L, count);
        }

        @Test
        @DisplayName("Positive: Should count users by specific role")
        void countByRole_ShouldReturnFilteredCount() {
            when(userRepository.countByRole("VEHICLE_OWNER")).thenReturn(10L);

            long count = userService.countByRole("VEHICLE_OWNER");

            assertEquals(10L, count);
        }

        @Test
        @DisplayName("Boundary: Should return zero when no users exist for a role")
        void countByRole_NoUsers_ShouldReturnZero() {
            when(userRepository.countByRole("ADMIN")).thenReturn(0L);

            long count = userService.countByRole("ADMIN");

            assertEquals(0L, count);
        }
    }
}
