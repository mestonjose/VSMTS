package com.team14.vsmts.service;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
