package com.team14.vsmts.controller;

import com.team14.vsmts.model.User;
import com.team14.vsmts.service.UserService;
import com.team14.vsmts.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        
        long totalUsers = userService.countByRole("VEHICLE_OWNER");
        long totalServiceCenters = userService.countByRole("SERVICE_CENTER");
        long totalVehicles = vehicleRepository.count();
        
        model.addAttribute("userCount", totalUsers);
        model.addAttribute("serviceCenterCount", totalServiceCenters);
        model.addAttribute("vehicleCount", totalVehicles);
        
        return "admin/dashboard";
    }
}
