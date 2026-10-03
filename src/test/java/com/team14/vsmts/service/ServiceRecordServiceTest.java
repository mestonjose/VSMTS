package com.team14.vsmts.service;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.repository.ServiceRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceRecordServiceTest {

    @Mock
    private ServiceRecordRepository serviceRecordRepository;

    @InjectMocks
    private ServiceRecordService serviceRecordService;

    private ServiceRecord testRecord;
    private Vehicle testVehicle;
    private User testServiceCenter;

    @BeforeEach
    void setUp() {
        testVehicle = new Vehicle();
        testVehicle.setId(1L);
        testVehicle.setMake("Toyota");

        testServiceCenter = new User();
        testServiceCenter.setId(1L);
        testServiceCenter.setRole("Service Center");

        testRecord = new ServiceRecord();
        testRecord.setId(1L);
        testRecord.setVehicle(testVehicle);
        testRecord.setServiceCenter(testServiceCenter);
        testRecord.setServiceDate(LocalDate.now());
        testRecord.setDescription("Oil Change");
        testRecord.setCost(50.0);
    }

    @Test
    void addServiceRecord_ShouldSaveAndReturnRecord() {
        when(serviceRecordRepository.save(testRecord)).thenReturn(testRecord);

        ServiceRecord savedRecord = serviceRecordService.addServiceRecord(testRecord);

        assertNotNull(savedRecord);
        assertEquals("Oil Change", savedRecord.getDescription());
        verify(serviceRecordRepository, times(1)).save(testRecord);
    }

    @Test
    void getRecordsByVehicle_ShouldReturnListOfRecords() {
        when(serviceRecordRepository.findByVehicle(testVehicle))
                .thenReturn(Arrays.asList(testRecord));

        List<ServiceRecord> records = serviceRecordService.getRecordsByVehicle(testVehicle);

        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
        assertEquals(testVehicle, records.get(0).getVehicle());
    }

    @Test
    void getRecordsByVehicles_ShouldReturnListOfRecords() {
        List<Vehicle> vehicles = Arrays.asList(testVehicle);
        when(serviceRecordRepository.findByVehicleIn(vehicles))
                .thenReturn(Arrays.asList(testRecord));

        List<ServiceRecord> records = serviceRecordService.getRecordsByVehicles(vehicles);

        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
    }

    @Test
    void getRecordsByServiceCenter_ShouldReturnListOfRecords() {
        when(serviceRecordRepository.findByServiceCenter(testServiceCenter))
                .thenReturn(Arrays.asList(testRecord));

        List<ServiceRecord> records = serviceRecordService.getRecordsByServiceCenter(testServiceCenter);

        assertFalse(records.isEmpty());
        assertEquals(1, records.size());
        assertEquals(testServiceCenter, records.get(0).getServiceCenter());
    }
}
