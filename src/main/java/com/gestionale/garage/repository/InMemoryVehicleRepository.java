package com.gestionale.garage.repository;

import com.gestionale.garage.model.Vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Legacy in-memory storage used only by {@link com.gestionale.garage.VehicleHttpServer}.
 * Spring Boot uses {@link VehicleRepository} (JPA) instead.
 */
public class InMemoryVehicleRepository {

    private final Map<Long, Vehicle> database = new HashMap<>();
    private Long currentId = 1L;

    public Vehicle add(Vehicle vehicle) {
        Vehicle savedVehicle = new Vehicle(
            currentId,
            vehicle.getMake(),
            vehicle.getModel(),
            vehicle.getYear(),
            vehicle.getPrice(),
            vehicle.getFuelType()
        );

        database.put(currentId, savedVehicle);
        currentId++;
        return savedVehicle;
    }

    public List<Vehicle> findAll() {
        return new ArrayList<>(database.values());
    }

    public Vehicle findById(Long id) {
        return database.get(id);
    }

    public Vehicle update(Long id, Vehicle updatedVehicle) {
        if (database.containsKey(id)) {
            Vehicle vehicleToSave = new Vehicle(
                id,
                updatedVehicle.getMake(),
                updatedVehicle.getModel(),
                updatedVehicle.getYear(),
                updatedVehicle.getPrice(),
                updatedVehicle.getFuelType()
            );
            database.put(id, vehicleToSave);
            return vehicleToSave;
        }
        return null;
    }

    public boolean delete(Long id) {
        if (database.containsKey(id)) {
            database.remove(id);
            return true;
        }
        return false;
    }
}
