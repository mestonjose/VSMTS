package com.team14.vsmts.service;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.ServiceRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * VSMTS-9: JUnit 5 tests for View Service History feature.
 * Tests the filter-by-status, date-range, and sorted-history methods.
 * Author: Kevin Jeniston S
 */
@ExtendWith(MockitoExtension.class)
public class ServiceRecordServiceHistoryTest {

    @Mock
    private ServiceRecordRepository serviceRecordRepository;

    @InjectMocks
    private ServiceRecordService serviceRecordService;

    @Test
    public void testGetRecordsByVehiclesAndStatus() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        List<Vehicle> vehicles = Collections.singletonList(vehicle);

        ServiceRecord record = new ServiceRecord();
        record.setStatus("COMPLETED");
        when(serviceRecordRepository.findByVehicleInAndStatus(vehicles, "COMPLETED"))
                .thenReturn(Collections.singletonList(record));

        List<ServiceRecord> result = serviceRecordService.getRecordsByVehiclesAndStatus(vehicles, "COMPLETED");
        assertEquals(1, result.size());
        assertEquals("COMPLETED", result.get(0).getStatus());
        verify(serviceRecordRepository).findByVehicleInAndStatus(vehicles, "COMPLETED");
    }

    @Test
    public void testGetRecordsByVehiclesAndDateRange() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        List<Vehicle> vehicles = Collections.singletonList(vehicle);
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 12, 31);

        ServiceRecord record = new ServiceRecord();
        record.setServiceDate(LocalDate.of(2026, 6, 15));
        when(serviceRecordRepository.findByVehicleInAndServiceDateBetween(vehicles, start, end))
                .thenReturn(Collections.singletonList(record));

        List<ServiceRecord> result = serviceRecordService.getRecordsByVehiclesAndDateRange(vehicles, start, end);
        assertEquals(1, result.size());
        verify(serviceRecordRepository).findByVehicleInAndServiceDateBetween(vehicles, start, end);
    }

    @Test
    public void testGetRecordsByServiceCenterAndStatus() {
        User serviceCenter = new User();
        serviceCenter.setId(1L);

        ServiceRecord record = new ServiceRecord();
        record.setStatus("PENDING");
        when(serviceRecordRepository.findByServiceCenterAndStatus(serviceCenter, "PENDING"))
                .thenReturn(Collections.singletonList(record));

        List<ServiceRecord> result = serviceRecordService.getRecordsByServiceCenterAndStatus(serviceCenter, "PENDING");
        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
        verify(serviceRecordRepository).findByServiceCenterAndStatus(serviceCenter, "PENDING");
    }

    @Test
    public void testGetRecordsByVehiclesSortedByDate() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        List<Vehicle> vehicles = Collections.singletonList(vehicle);

        ServiceRecord record1 = new ServiceRecord();
        record1.setServiceDate(LocalDate.of(2026, 9, 1));
        ServiceRecord record2 = new ServiceRecord();
        record2.setServiceDate(LocalDate.of(2026, 3, 15));

        when(serviceRecordRepository.findByVehicleInOrderByServiceDateDesc(vehicles))
                .thenReturn(Arrays.asList(record1, record2));

        List<ServiceRecord> result = serviceRecordService.getRecordsByVehiclesSortedByDate(vehicles);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getServiceDate().isAfter(result.get(1).getServiceDate()));
        verify(serviceRecordRepository).findByVehicleInOrderByServiceDateDesc(vehicles);
    }

    @Test
    public void testGetRecordsByVehiclesAndStatusEmpty() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        List<Vehicle> vehicles = Collections.singletonList(vehicle);

        when(serviceRecordRepository.findByVehicleInAndStatus(vehicles, "IN_PROGRESS"))
                .thenReturn(Collections.emptyList());

        List<ServiceRecord> result = serviceRecordService.getRecordsByVehiclesAndStatus(vehicles, "IN_PROGRESS");
        assertTrue(result.isEmpty());
    }
}
