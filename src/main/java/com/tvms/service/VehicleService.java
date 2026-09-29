package com.tvms.service;

/**
 * Service for handling vehicle registration in the TVMS system.
 */
public class VehicleService {

    /**
     * Registers a new vehicle with the given details.
     *
     * @param plateNumber the vehicle's plate number
     * @param type        the vehicle type (e.g., Bus, Truck, Van)
     * @param capacity    the vehicle's capacity (must be greater than 0)
     * @return true if registration is successful
     * @throws IllegalArgumentException if capacity is less than or equal to 0
     */
    public boolean registerVehicle(String plateNumber, String type, int capacity) {
        // Validate capacity
        if (capacity <= 0) {
            throw new IllegalArgumentException("Vehicle capacity must be greater than 0. Received: " + capacity);
        }

        // Registration successful
        return true;
    }
}
