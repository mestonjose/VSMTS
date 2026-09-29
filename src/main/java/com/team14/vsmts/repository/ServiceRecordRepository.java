package com.team14.vsmts.repository;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByVehicle(Vehicle vehicle);
    List<ServiceRecord> findByVehicleIn(List<Vehicle> vehicles);
    List<ServiceRecord> findByServiceCenter(User serviceCenter);
}
