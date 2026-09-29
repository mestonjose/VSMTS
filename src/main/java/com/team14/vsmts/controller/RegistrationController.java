package com.team14.vsmts.controller;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                               BindingResult result, Model model) {

        if (userService.emailExists(userDto.getEmail())) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
        }

        if (!userDto.getPassword().isEmpty() && !userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.registerUser(userDto);
        return "redirect:/login?registered";
    }
}
