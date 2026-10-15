package com.team14.vsmts.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * VSMTS-12: Maintenance Reminder Notification
 * Represents a scheduled maintenance reminder for a vehicle.
 * Owners can set reminders for upcoming services (oil change, tyre rotation, etc.)
 */
@Entity
@Table(name = "maintenance_reminders")
public class MaintenanceReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // e.g. "Oil Change", "Tyre Rotation"

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;

    private boolean completed = false;

    private boolean snoozed = false;

    @Column(name = "snooze_until")
    private LocalDate snoozeUntil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public MaintenanceReminder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isSnoozed() { return snoozed; }
    public void setSnoozed(boolean snoozed) { this.snoozed = snoozed; }

    public LocalDate getSnoozeUntil() { return snoozeUntil; }
    public void setSnoozeUntil(LocalDate snoozeUntil) { this.snoozeUntil = snoozeUntil; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    /** Returns true if reminder is due today or overdue (and not snoozed past today) */
    public boolean isUrgent() {
        if (completed) return false;
        if (snoozed && snoozeUntil != null && !LocalDate.now().isAfter(snoozeUntil)) return false;
        return !dueDate.isAfter(LocalDate.now().plusDays(7)); // urgent if due within 7 days
    }
}
