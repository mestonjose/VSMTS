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
public class OwnerControllerCoverageTest {

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
    private OwnerController controller;

    @Test
    public void testShowAddVehicleForm() {
        assertEquals("owner/add-vehicle", controller.showAddVehicleForm(model));
    }

    @Test
    public void testAddVehicleSuccess() {
        Vehicle v = new Vehicle();
        v.setLicensePlate("123");
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(vehicleService.licensePlateExists(anyString())).thenReturn(false);
        assertEquals("redirect:/owner/dashboard?vehicleAdded", controller.addVehicle(v, auth, model));
    }

    @Test
    public void testAddVehicleFail() {
        Vehicle v = new Vehicle();
        v.setLicensePlate("123");
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        when(vehicleService.licensePlateExists(anyString())).thenReturn(true);
        assertEquals("owner/add-vehicle", controller.addVehicle(v, auth, model));
    }

    @Test
    public void testListVehicles() {
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        assertEquals("owner/vehicles", controller.listVehicles(auth, model));
    }

    @Test
    public void testServiceHistory() {
        when(auth.getName()).thenReturn("t@t.com");
        when(userService.findByEmail(anyString())).thenReturn(new User());
        assertEquals("owner/service-history", controller.serviceHistory(auth, model));
    }
}
