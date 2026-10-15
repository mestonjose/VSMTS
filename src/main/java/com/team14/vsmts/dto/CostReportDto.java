package com.team14.vsmts.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * VSMTS-14: Cost Dashboard & Reports DTO
 * Aggregates cost metrics, vehicle spending, and service type distributions.
 */
public class CostReportDto {

    private double totalCost = 0.0;
    private int totalServices = 0;
    private double averageCost = 0.0;
    private double highestCost = 0.0;

    private Map<String, Double> costByServiceType = new LinkedHashMap<>();
    private Map<String, Double> costByVehicle = new LinkedHashMap<>();
    private Map<String, Double> monthlyTrends = new LinkedHashMap<>();
    private List<VehicleCostSummary> vehicleSummaries = new ArrayList<>();

    public static class VehicleCostSummary {
        private String vehicleName;
        private String licensePlate;
        private int serviceCount;
        private double totalSpent;
        private double averageSpent;

        public VehicleCostSummary(String vehicleName, String licensePlate, int serviceCount, double totalSpent, double averageSpent) {
            this.vehicleName = vehicleName;
            this.licensePlate = licensePlate;
            this.serviceCount = serviceCount;
            this.totalSpent = totalSpent;
            this.averageSpent = averageSpent;
        }

        public String getVehicleName() { return vehicleName; }
        public String getLicensePlate() { return licensePlate; }
        public int getServiceCount() { return serviceCount; }
        public double getTotalSpent() { return totalSpent; }
        public double getAverageSpent() { return averageSpent; }
    }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public int getTotalServices() { return totalServices; }
    public void setTotalServices(int totalServices) { this.totalServices = totalServices; }

    public double getAverageCost() { return averageCost; }
    public void setAverageCost(double averageCost) { this.averageCost = averageCost; }

    public double getHighestCost() { return highestCost; }
    public void setHighestCost(double highestCost) { this.highestCost = highestCost; }

    public Map<String, Double> getCostByServiceType() { return costByServiceType; }
    public void setCostByServiceType(Map<String, Double> costByServiceType) { this.costByServiceType = costByServiceType; }

    public Map<String, Double> getCostByVehicle() { return costByVehicle; }
    public void setCostByVehicle(Map<String, Double> costByVehicle) { this.costByVehicle = costByVehicle; }

    public Map<String, Double> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(Map<String, Double> monthlyTrends) { this.monthlyTrends = monthlyTrends; }

    public List<VehicleCostSummary> getVehicleSummaries() { return vehicleSummaries; }
    public void setVehicleSummaries(List<VehicleCostSummary> vehicleSummaries) { this.vehicleSummaries = vehicleSummaries; }
}
