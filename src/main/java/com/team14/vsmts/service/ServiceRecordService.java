package com.team14.vsmts.service;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
