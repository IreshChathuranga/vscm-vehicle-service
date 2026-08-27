package com.autocare.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(@NotNull Long customerId, @NotBlank String registrationNumber,
                             @NotBlank String manufacturer, @NotBlank String model, @Min(1886) Integer manufacturedYear,
                             String fuelType, String transmissionType, String engineNumber, String chassisNumber,
                             @NotNull @Min(0) Integer currentMileage) {
}
