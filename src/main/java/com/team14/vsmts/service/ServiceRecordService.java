package com.team14.vsmts.service;

import com.team14.vsmts.dto.CostReportDto;
import com.team14.vsmts.model.ServiceRecord;
import com.team14.vsmts.model.Vehicle;
import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.ServiceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    // VSMTS-14: Cost Dashboard & Reports
    public CostReportDto calculateCostReport(List<Vehicle> vehicles) {
        CostReportDto report = new CostReportDto();
        if (vehicles == null || vehicles.isEmpty()) {
            return report;
        }

        List<ServiceRecord> records = getRecordsByVehicles(vehicles);
        if (records.isEmpty()) {
            return report;
        }

        double total = 0.0;
        double max = 0.0;
        Map<String, Double> byType = new LinkedHashMap<>();
        Map<String, Double> byVehicle = new LinkedHashMap<>();
        Map<String, Double> byMonth = new TreeMap<>();

        Map<Long, List<ServiceRecord>> vehicleGroup = records.stream()
                .collect(Collectors.groupingBy(r -> r.getVehicle().getId()));

        for (ServiceRecord r : records) {
            double cost = r.getCost();
            total += cost;
            if (cost > max) max = cost;

            // By Service Type
            String type = (r.getServiceType() != null && !r.getServiceType().trim().isEmpty()) ? r.getServiceType() : "General";
            byType.put(type, byType.getOrDefault(type, 0.0) + cost);

            // By Vehicle
            String vKey = r.getVehicle().getMake() + " " + r.getVehicle().getModel() + " (" + r.getVehicle().getLicensePlate() + ")";
            byVehicle.put(vKey, byVehicle.getOrDefault(vKey, 0.0) + cost);

            // Monthly Trend
            if (r.getServiceDate() != null) {
                String monthKey = r.getServiceDate().getYear() + "-" + String.format("%02d", r.getServiceDate().getMonthValue());
                byMonth.put(monthKey, byMonth.getOrDefault(monthKey, 0.0) + cost);
            }
        }

        report.setTotalCost(total);
        report.setTotalServices(records.size());
        report.setAverageCost(records.size() > 0 ? (total / records.size()) : 0.0);
        report.setHighestCost(max);
        report.setCostByServiceType(byType);
        report.setCostByVehicle(byVehicle);
        report.setMonthlyTrends(byMonth);

        List<CostReportDto.VehicleCostSummary> summaries = new ArrayList<>();
        for (Vehicle v : vehicles) {
            List<ServiceRecord> vRecs = vehicleGroup.getOrDefault(v.getId(), Collections.emptyList());
            double vTotal = vRecs.stream().mapToDouble(ServiceRecord::getCost).sum();
            double vAvg = vRecs.isEmpty() ? 0.0 : (vTotal / vRecs.size());
            summaries.add(new CostReportDto.VehicleCostSummary(
                    v.getMake() + " " + v.getModel(),
                    v.getLicensePlate(),
                    vRecs.size(),
                    vTotal,
                    vAvg
            ));
        }
        report.setVehicleSummaries(summaries);

        return report;
    }
}
