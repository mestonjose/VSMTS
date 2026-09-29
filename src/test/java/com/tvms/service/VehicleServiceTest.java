package com.tvms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link VehicleService}.
 */
class VehicleServiceTest {

    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService();
    }

    @Test
    void testValidVehicleRegistration() {
        boolean result = vehicleService.registerVehicle("ABC-1234", "Truck", 5000);
        assertTrue(result, "Valid vehicle registration should return true.");
    }

    @Test
    void testInvalidVehicleCapacity() {
        assertThrows(IllegalArgumentException.class, () ->
                vehicleService.registerVehicle("XYZ-9999", "Van", -100),
                "Negative capacity should throw IllegalArgumentException."
        );
    }
}
