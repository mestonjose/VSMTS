package com.team14.vsmts.controller;

import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.service.ServiceRecordService;
import com.team14.vsmts.service.UserService;
import com.team14.vsmts.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceCenterControllerCoverageTest {

    @Mock
    private UserService userService;
    @Mock
    private VehicleService vehicleService;
    @Mock
    private ServiceRecordService serviceRecordService;
    @Mock
    private Model model;
    @Mock
    private Authentication auth;

    @InjectMocks
    private ServiceCenterController controller;

    @Test
    public void testShowLogServiceForm() {
        assertEquals("service-center/log-service", controller.showLogServiceForm(model));
    }

    @Test
    public void testLogServiceSuccess() {
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(vehicleService.getVehicleById(anyLong())).thenReturn(new Vehicle());
        assertEquals("redirect:/service-center/dashboard?serviceLogged", controller.logService(1L, "Type", "Desc", "2023-01-01", 10.0, "COMPLETED", auth, model));
    }

    @Test
    public void testLogServiceFail() {
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(vehicleService.getVehicleById(anyLong())).thenReturn(null);
        assertEquals("service-center/log-service", controller.logService(1L, "Type", "Desc", "2023-01-01", 10.0, "COMPLETED", auth, model));
    }

    @Test
    public void testViewRecords() {
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        assertEquals("service-center/records", controller.viewRecords(auth, model));
    }
}
