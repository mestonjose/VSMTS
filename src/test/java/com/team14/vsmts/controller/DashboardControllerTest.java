package com.team14.vsmts.controller;

import com.team14.vsmts.model.User;
import com.team14.vsmts.service.UserService;
import com.team14.vsmts.service.VehicleService;
import com.team14.vsmts.service.ServiceRecordService;
import com.team14.vsmts.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DashboardControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private VehicleService vehicleService;

    @Mock
    private ServiceRecordService serviceRecordService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private AdminController adminController;

    @InjectMocks
    private OwnerController ownerController;

    @InjectMocks
    private ServiceCenterController serviceCenterController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@test.com");
    }

    // ==========================================
    // VSMTS-4: Role-Based Dashboard Logic
    // ==========================================
    @Nested
    @DisplayName("VSMTS-4: Dashboard Logic Tests")
    class DashboardTests {

        @Test
        @DisplayName("Positive: Admin Dashboard should load correct counts and view")
        void adminDashboard_ShouldLoadProperly() {
            when(userDetails.getUsername()).thenReturn("admin@test.com");
            when(userService.findByEmail("admin@test.com")).thenReturn(testUser);
            when(userService.countByRole("VEHICLE_OWNER")).thenReturn(10L);
            when(userService.countByRole("SERVICE_CENTER")).thenReturn(5L);
            when(vehicleRepository.count()).thenReturn(20L);

            String viewName = adminController.adminDashboard(userDetails, model);

            assertEquals("admin/dashboard", viewName);
            verify(model).addAttribute("user", testUser);
            verify(model).addAttribute("userCount", 10L);
            verify(model).addAttribute("serviceCenterCount", 5L);
            verify(model).addAttribute("vehicleCount", 20L);
        }

        @Test
        @DisplayName("Positive: Owner Dashboard should fetch user vehicles and view")
        void ownerDashboard_ShouldLoadProperly() {
            when(authentication.getName()).thenReturn("owner@test.com");
            when(userService.findByEmail("owner@test.com")).thenReturn(testUser);
            when(vehicleService.getVehiclesByOwner(testUser)).thenReturn(Collections.emptyList());
            when(serviceRecordService.getRecordsByVehicles(anyList())).thenReturn(Collections.emptyList());

            String viewName = ownerController.dashboard(authentication, model);

            assertEquals("owner/dashboard", viewName);
            verify(model).addAttribute("user", testUser);
            verify(model).addAttribute("vehicles", Collections.emptyList());
            verify(model).addAttribute("records", Collections.emptyList());
        }

        @Test
        @DisplayName("Positive: Service Center Dashboard should fetch records and view")
        void serviceCenterDashboard_ShouldLoadProperly() {
            when(authentication.getName()).thenReturn("sc@test.com");
            when(userService.findByEmail("sc@test.com")).thenReturn(testUser);
            when(serviceRecordService.getRecordsByServiceCenter(testUser)).thenReturn(Collections.emptyList());

            String viewName = serviceCenterController.dashboard(authentication, model);

            assertEquals("service-center/dashboard", viewName);
            verify(model).addAttribute("user", testUser);
            verify(model).addAttribute("records", Collections.emptyList());
            verify(model).addAttribute("recordCount", 0);
        }
    }
}
