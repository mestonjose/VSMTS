package com.team14.vsmts.controller;

import com.team14.vsmts.model.PasswordResetToken;
import com.team14.vsmts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @Autowired
    private UserService userService;

    // Show the "Forgot Password" form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    // Handle the "Forgot Password" form submission
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        String token = userService.generatePasswordResetToken(email);

        if (token == null) {
            model.addAttribute("error", "No account found with that email address.");
            return "forgot-password";
        }

        model.addAttribute("success", "Password reset link has been sent to your email. Check your console/inbox.");
        return "forgot-password";
    }

    // Show the "Reset Password" form (user clicks the link from the email)
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = userService.findByToken(token);

        if (resetToken == null) {
            model.addAttribute("error", "Invalid reset token.");
            return "forgot-password";
        }
        if (resetToken.isExpired()) {
            model.addAttribute("error", "This reset link has expired. Please request a new one.");
            return "forgot-password";
        }
        if (resetToken.isUsed()) {
            model.addAttribute("error", "This reset link has already been used.");
            return "forgot-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    // Handle the "Reset Password" form submission
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters.");
            model.addAttribute("token", token);
            return "reset-password";
        }

        String errorMessage = userService.resetPassword(token, password);

        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "forgot-password";
        }

        return "redirect:/login?resetSuccess";
    }
}
