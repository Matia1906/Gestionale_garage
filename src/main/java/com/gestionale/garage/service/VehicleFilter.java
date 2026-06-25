package com.gestionale.garage.service;

import com.gestionale.garage.model.FuelType;

public record VehicleFilter(
        String make,
        String model,
        Integer year,
        Double price,
        FuelType fuelType
) {
}
