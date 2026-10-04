package com.team14.vsmts.model;

import com.team14.vsmts.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ModelCoverageTest {

    @Test
    public void testUser() {
        User user = new User("John Doe", "john@test.com", "pass", "ADMIN");
        user.setId(1L);
        user.setFullName("Jane Doe");
        user.setEmail("jane@test.com");
        user.setPassword("newpass");
        user.setRole("USER");
        
        assertEquals(1L, user.getId());
        assertEquals("Jane Doe", user.getFullName());
        assertEquals("jane@test.com", user.getEmail());
        assertEquals("newpass", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    public void testVehicle() {
        Vehicle v = new Vehicle();
        v.setId(2L);
        v.setMake("Toyota");
        v.setModel("Camry");
        v.setYear(2020);
        v.setLicensePlate("ABC-123");
        v.setColor("Red");
        User owner = new User();
        v.setOwner(owner);
        
        assertEquals(2L, v.getId());
        assertEquals("Toyota", v.getMake());
        assertEquals("Camry", v.getModel());
        assertEquals(2020, v.getYear());
        assertEquals("ABC-123", v.getLicensePlate());
        assertEquals("Red", v.getColor());
        assertEquals(owner, v.getOwner());
    }

    @Test
    public void testServiceRecord() {
        ServiceRecord sr = new ServiceRecord();
        sr.setId(3L);
        sr.setServiceType("Oil Change");
        sr.setDescription("Changed oil");
        sr.setServiceDate(LocalDate.now());
        sr.setCost(50.0);
        sr.setStatus("COMPLETED");
        Vehicle v = new Vehicle();
        sr.setVehicle(v);
        User sc = new User();
        sr.setServiceCenter(sc);
        
        assertEquals(3L, sr.getId());
        assertEquals("Oil Change", sr.getServiceType());
        assertEquals("Changed oil", sr.getDescription());
        assertNotNull(sr.getServiceDate());
        assertEquals(50.0, sr.getCost());
        assertEquals("COMPLETED", sr.getStatus());
        assertEquals(v, sr.getVehicle());
        assertEquals(sc, sr.getServiceCenter());
    }

    @Test
    public void testUserRegistrationDto() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setFullName("Test");
        dto.setEmail("test@test");
        dto.setPassword("pass");
        dto.setConfirmPassword("pass");
        dto.setRole("OWNER");
        
        assertEquals("Test", dto.getFullName());
        assertEquals("test@test", dto.getEmail());
        assertEquals("pass", dto.getPassword());
        assertEquals("pass", dto.getConfirmPassword());
        assertEquals("OWNER", dto.getRole());
    }
}
