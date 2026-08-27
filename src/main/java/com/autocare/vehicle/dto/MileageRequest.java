package com.autocare.vehicle.dto;

import jakarta.validation.constraints.Min;

public record MileageRequest(@Min(0) int mileage) {
}
