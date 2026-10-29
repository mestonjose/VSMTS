package com.team14.vsmts.repository;

import com.team14.vsmts.model.MaintenanceReminder;
import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceReminderRepository extends JpaRepository<MaintenanceReminder, Long> {
    List<MaintenanceReminder> findByOwner(User owner);
    List<MaintenanceReminder> findByOwnerAndCompletedFalse(User owner);
    List<MaintenanceReminder> findByOwnerAndCompletedFalseOrderByDueDateAsc(User owner);
    List<MaintenanceReminder> findByVehicle(Vehicle vehicle);
    List<MaintenanceReminder> findByOwnerAndCompletedFalseAndDueDateLessThanEqual(User owner, LocalDate date);
    List<MaintenanceReminder> findByOwnerAndCompletedTrueOrderByDueDateDesc(User owner);
}
