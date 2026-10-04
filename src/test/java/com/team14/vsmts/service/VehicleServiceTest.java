package com.team14.vsmts.service;

import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @Mock
    private VehicleRepository repo;
    @InjectMocks
    private VehicleService service;

    @Test
    public void testGetVehiclesByOwner() {
        when(repo.findByOwner(any())).thenReturn(Collections.emptyList());
        assertTrue(service.getVehiclesByOwner(new User()).isEmpty());
    }
    @Test
    public void testGetAllVehicles() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        assertTrue(service.getAllVehicles().isEmpty());
    }
    @Test
    public void testLicensePlateExists() {
        when(repo.existsByLicensePlate("123")).thenReturn(true);
        assertTrue(service.licensePlateExists("123"));
    }
    @Test
    public void testAddVehicle() {
        Vehicle v = new Vehicle();
        service.addVehicle(v);
        verify(repo).save(v);
    }
    @Test
    public void testGetVehicleById() {
        Vehicle v = new Vehicle();
        when(repo.findById(1L)).thenReturn(Optional.of(v));
        assertEquals(v, service.getVehicleById(1L));
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertNull(service.getVehicleById(2L));
    }
}
