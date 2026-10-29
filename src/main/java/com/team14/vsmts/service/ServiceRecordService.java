package com.team14.vsmts.service;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRecordService {

    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    public ServiceRecord addServiceRecord(ServiceRecord record) {
        return serviceRecordRepository.save(record);
    }

    public List<ServiceRecord> getRecordsByVehicle(Vehicle vehicle) {
        return serviceRecordRepository.findByVehicle(vehicle);
    }

    public List<ServiceRecord> getRecordsByVehicles(List<Vehicle> vehicles) {
        return serviceRecordRepository.findByVehicleIn(vehicles);
    }

    public List<ServiceRecord> getRecordsByServiceCenter(User serviceCenter) {
        return serviceRecordRepository.findByServiceCenter(serviceCenter);
    }

    // VSMTS-9: View Service History - filter and search methods
    public List<ServiceRecord> getRecordsByVehiclesAndStatus(List<Vehicle> vehicles, String status) {
        return serviceRecordRepository.findByVehicleInAndStatus(vehicles, status);
    }

    public List<ServiceRecord> getRecordsByVehiclesAndDateRange(List<Vehicle> vehicles, LocalDate startDate, LocalDate endDate) {
        return serviceRecordRepository.findByVehicleInAndServiceDateBetween(vehicles, startDate, endDate);
    }

    public List<ServiceRecord> getRecordsByServiceCenterAndStatus(User serviceCenter, String status) {
        return serviceRecordRepository.findByServiceCenterAndStatus(serviceCenter, status);
    }

    public List<ServiceRecord> getRecordsByVehiclesSortedByDate(List<Vehicle> vehicles) {
        return serviceRecordRepository.findByVehicleInOrderByServiceDateDesc(vehicles);
    }

    // VSMTS-10: Filter service records by date range, service type, and status
    public List<ServiceRecord> filterRecords(List<Vehicle> vehicles, LocalDate startDate, LocalDate endDate,
                                              String serviceType, String status) {
        List<ServiceRecord> records = serviceRecordRepository.findByVehicleInOrderByServiceDateDesc(vehicles);

        return records.stream()
                .filter(r -> startDate == null || !r.getServiceDate().isBefore(startDate))
                .filter(r -> endDate == null || !r.getServiceDate().isAfter(endDate))
                .filter(r -> serviceType == null || serviceType.isEmpty() || serviceType.equalsIgnoreCase(r.getServiceType()))
                .filter(r -> status == null || status.isEmpty() || status.equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
    }
}
