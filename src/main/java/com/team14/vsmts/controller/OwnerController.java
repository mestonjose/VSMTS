package com.team14.vsmts.controller;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.service.ServiceRecordService;
import com.team14.vsmts.service.UserService;
import com.team14.vsmts.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServiceRecordService serviceRecordService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);
        List<ServiceRecord> records = serviceRecordService.getRecordsByVehicles(vehicles);

        model.addAttribute("user", user);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("records", records);
        model.addAttribute("vehicleCount", vehicles.size());
        model.addAttribute("recordCount", records.size());
        return "owner/dashboard";
    }

    @GetMapping("/vehicles/add")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "owner/add-vehicle";
    }

    @PostMapping("/vehicles/add")
    public String addVehicle(@ModelAttribute Vehicle vehicle, Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());

        if (vehicleService.licensePlateExists(vehicle.getLicensePlate())) {
            model.addAttribute("error", "A vehicle with this license plate already exists");
            model.addAttribute("vehicle", vehicle);
            return "owner/add-vehicle";
        }

        vehicle.setOwner(user);
        vehicleService.addVehicle(vehicle);
        return "redirect:/owner/dashboard?vehicleAdded";
    }

    @GetMapping("/vehicles")
    public String listVehicles(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("vehicles", vehicleService.getVehiclesByOwner(user));
        return "owner/vehicles";
    }

    // VSMTS-10: Filter service records by date range, service type, and status
    @GetMapping("/service-history")
    public String serviceHistory(@RequestParam(required = false) String startDate,
                                  @RequestParam(required = false) String endDate,
                                  @RequestParam(required = false) String serviceType,
                                  @RequestParam(required = false) String status,
                                  Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);

        LocalDate start = (startDate != null && !startDate.isEmpty()) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null && !endDate.isEmpty()) ? LocalDate.parse(endDate) : null;

        List<ServiceRecord> records = serviceRecordService.filterRecords(vehicles, start, end, serviceType, status);

        model.addAttribute("records", records);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("status", status);
        return "owner/service-history";
    }
}
