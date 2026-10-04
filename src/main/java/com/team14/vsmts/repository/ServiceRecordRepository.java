package com.team14.vsmts.repository;

import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByVehicle(Vehicle vehicle);
    List<ServiceRecord> findByVehicleIn(List<Vehicle> vehicles);
    List<ServiceRecord> findByServiceCenter(User serviceCenter);

    // VSMTS-9: View Service History - filter and search methods
    List<ServiceRecord> findByVehicleInAndStatus(List<Vehicle> vehicles, String status);
    List<ServiceRecord> findByVehicleInAndServiceDateBetween(List<Vehicle> vehicles, LocalDate startDate, LocalDate endDate);
    List<ServiceRecord> findByServiceCenterAndStatus(User serviceCenter, String status);
    List<ServiceRecord> findByVehicleInOrderByServiceDateDesc(List<Vehicle> vehicles);
}
