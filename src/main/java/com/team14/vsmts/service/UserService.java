package com.team14.vsmts.service;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.model.PasswordResetToken;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.PasswordResetTokenRepository;
import com.team14.vsmts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public User registerUser(UserRegistrationDto dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        String role = "VEHICLE_OWNER";
        if (dto.getRole().equals("Service Center")) role = "SERVICE_CENTER";
        if (dto.getRole().equals("Administrator")) role = "ADMIN";
        user.setRole(role);

        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public long countAll() {
        return userRepository.count();
    }

    public long countByRole(String role) {
        return userRepository.countByRole(role);
    }

    // ==================== PASSWORD RESET ====================

    public String generatePasswordResetToken(String email) {
        User user = findByEmail(email);
        if (user == null) {
            return null;
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                token, user, LocalDateTime.now().plusMinutes(30)
        );
        tokenRepository.save(resetToken);

        // MOCK EMAIL SERVICE - prints the reset link to the console
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        System.out.println("\n====================================================");
        System.out.println("  [MOCK EMAIL SERVICE] Password Reset Request");
        System.out.println("  To: " + email);
        System.out.println("  Subject: Reset Your DriveLedger Password");
        System.out.println("----------------------------------------------------");
        System.out.println("  Click the link below to reset your password:");
        System.out.println("  --> " + resetLink);
        System.out.println("  (This link expires in 30 minutes)");
        System.out.println("====================================================\n");

        return token;
    }

    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElse(null);

        if (resetToken == null) {
            return "Invalid reset token.";
        }
        if (resetToken.isUsed()) {
            return "This reset link has already been used.";
        }
        if (resetToken.isExpired()) {
            return "This reset link has expired. Please request a new one.";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        return null; // null means success
    }

    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token).orElse(null);
    }
}
