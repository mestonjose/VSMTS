package com.team14.vsmts.repository;

import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwner(User owner);
    boolean existsByLicensePlate(String licensePlate);
}
