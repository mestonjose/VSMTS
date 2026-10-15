package com.team14.vsmts.service;

import com.team14.vsmts.model.MaintenanceReminder;
import com.team14.vsmts.model.User;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.repository.MaintenanceReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceReminderService {

    @Autowired
    private MaintenanceReminderRepository reminderRepository;

    public MaintenanceReminder addReminder(MaintenanceReminder reminder) {
        return reminderRepository.save(reminder);
    }

    public List<MaintenanceReminder> getRemindersByOwner(User owner) {
        return reminderRepository.findByOwner(owner);
    }

    public List<MaintenanceReminder> getActiveReminders(User owner) {
        return reminderRepository.findByOwnerAndCompletedFalseOrderByDueDateAsc(owner);
    }

    public List<MaintenanceReminder> getDueNotifications(User owner) {
        LocalDate threshold = LocalDate.now().plusDays(7);
        List<MaintenanceReminder> active = reminderRepository.findByOwnerAndCompletedFalseOrderByDueDateAsc(owner);
        LocalDate today = LocalDate.now();

        return active.stream()
                .filter(r -> {
                    if (r.isSnoozed() && r.getSnoozeUntil() != null && !today.isAfter(r.getSnoozeUntil())) {
                        return false;
                    }
                    return !r.getDueDate().isAfter(threshold);
                })
                .collect(Collectors.toList());
    }

    public MaintenanceReminder findById(Long id) {
        return reminderRepository.findById(id).orElse(null);
    }

    public void completeReminder(Long id) {
        MaintenanceReminder reminder = findById(id);
        if (reminder != null) {
            reminder.setCompleted(true);
            reminderRepository.save(reminder);
        }
    }

    public void snoozeReminder(Long id, int days) {
        MaintenanceReminder reminder = findById(id);
        if (reminder != null) {
            reminder.setSnoozed(true);
            reminder.setSnoozeUntil(LocalDate.now().plusDays(days));
            reminderRepository.save(reminder);
        }
    }
}
