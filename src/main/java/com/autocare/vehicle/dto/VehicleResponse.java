package com.autocare.vehicle.dto;

import java.time.LocalDateTime;

public record VehicleResponse(Long id, String vehicleCode, Long customerId, String registrationNumber,
                              String manufacturer, String model, Integer manufacturedYear, String fuelType,
                              String transmissionType, String engineNumber, String chassisNumber,
                              Integer currentMileage, String vehicleImagePath, boolean active, LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
