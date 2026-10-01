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
@RequestMapping("/service-center")
public class ServiceCenterController {

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ServiceRecordService serviceRecordService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        List<ServiceRecord> records = serviceRecordService.getRecordsByServiceCenter(user);

        model.addAttribute("user", user);
        model.addAttribute("records", records);
        model.addAttribute("recordCount", records.size());
        return "service-center/dashboard";
    }

    @GetMapping("/log-service")
    public String showLogServiceForm(Model model) {
        model.addAttribute("record", new ServiceRecord());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        return "service-center/log-service";
    }

    @PostMapping("/log-service")
    public String logService(@RequestParam Long vehicleId,
                             @RequestParam String serviceType,
                             @RequestParam String description,
                             @RequestParam String serviceDate,
                             @RequestParam double cost,
                             @RequestParam String status,
                             Authentication auth, Model model) {
        User serviceCenter = userService.findByEmail(auth.getName());
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);

        if (vehicle == null) {
            model.addAttribute("error", "Vehicle not found");
            model.addAttribute("vehicles", vehicleService.getAllVehicles());
            return "service-center/log-service";
        }

        ServiceRecord record = new ServiceRecord();
        record.setVehicle(vehicle);
        record.setServiceCenter(serviceCenter);
        record.setServiceType(serviceType);
        record.setDescription(description);
        record.setServiceDate(LocalDate.parse(serviceDate));
        record.setCost(cost);
        record.setStatus(status);

        serviceRecordService.addServiceRecord(record);
        return "redirect:/service-center/dashboard?serviceLogged";
    }

    @GetMapping("/records")
    public String viewRecords(Authentication auth, Model model) {
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("records", serviceRecordService.getRecordsByServiceCenter(user));
        return "service-center/records";
    }
}
