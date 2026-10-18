package com.team14.vsmts.controller;

import com.team14.vsmts.model.MaintenanceReminder;
import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.service.MaintenanceReminderService;
import com.team14.vsmts.service.ServiceRecordService;
import com.team14.vsmts.service.UserService;
import com.team14.vsmts.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private MaintenanceReminderService reminderService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);
        List<ServiceRecord> records = serviceRecordService.getRecordsByVehicles(vehicles);
        List<MaintenanceReminder> urgentReminders = reminderService.getDueNotifications(user);

        model.addAttribute("user", user);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("records", records);
        model.addAttribute("vehicleCount", vehicles.size());
        model.addAttribute("recordCount", records.size());
        model.addAttribute("reminders", urgentReminders);
        model.addAttribute("reminderCount", urgentReminders.size());
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

    @GetMapping("/service-history")
    public String serviceHistory(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);
        List<ServiceRecord> records = serviceRecordService.getRecordsByVehicles(vehicles);
        model.addAttribute("records", records);
        model.addAttribute("vehicles", vehicles);
        return "owner/service-history";
    }

    // VSMTS-12 & VSMTS-13: Maintenance Reminders (Snooze & Complete)
    @GetMapping("/reminders")
    public String listReminders(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<MaintenanceReminder> activeReminders = reminderService.getActiveReminders(user);
        List<MaintenanceReminder> completedReminders = reminderService.getCompletedReminders(user);
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);

        model.addAttribute("reminders", activeReminders);
        model.addAttribute("completedReminders", completedReminders);
        model.addAttribute("vehicles", vehicles);
        return "owner/reminders";
    }

    @GetMapping("/reminders/add")
    public String showAddReminderForm(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(user);

        model.addAttribute("reminder", new MaintenanceReminder());
        model.addAttribute("vehicles", vehicles);
        return "owner/add-reminder";
    }

    @PostMapping("/reminders/add")
    public String addReminder(@ModelAttribute MaintenanceReminder reminder,
                              @RequestParam("vehicleId") Long vehicleId,
                              Authentication auth) {
        User user = userService.findByEmail(auth.getName());
        Vehicle vehicle = vehicleService.getVehiclesByOwner(user).stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst().orElse(null);

        if (vehicle != null) {
            reminder.setOwner(user);
            reminder.setVehicle(vehicle);
            reminderService.addReminder(reminder);
        }
        return "redirect:/owner/reminders?added";
    }

    @PostMapping("/reminders/{id}/complete")
    public String completeReminder(@PathVariable Long id) {
        reminderService.completeReminder(id);
        return "redirect:/owner/reminders?completed";
    }

    @PostMapping("/reminders/{id}/snooze")
    public String snoozeReminder(@PathVariable Long id, @RequestParam(defaultValue = "7") int days) {
        reminderService.snoozeReminder(id, days);
        return "redirect:/owner/reminders?snoozed";
    }

    // VSMTS-13: Re-open completed reminder
    @PostMapping("/reminders/{id}/reopen")
    public String reopenReminder(@PathVariable Long id) {
        reminderService.reopenReminder(id);
        return "redirect:/owner/reminders?reopened";
    }
}

