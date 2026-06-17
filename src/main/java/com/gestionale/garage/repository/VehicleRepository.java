package com.gestionale.garage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRepository {
    // In-memory storage acting as our database table
    private final Map<Long, Vehicle> database = new HashMap<>();
    // Counter for auto-incrementing IDs
    private Long currentId = 1L;

    // CREATE (Add a new vehicle)
    public Vehicle add(Vehicle vehicle) {
        // Create a new instance to assign the automatically generated ID
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

    // READ ALL (Find all vehicles)
    public List<Vehicle> findAll() {
        return new ArrayList<>(database.values());
    }

    // READ ONE (Find a vehicle by ID)
    public Vehicle findById(Long id) {
        return database.get(id);
    }

    // UPDATE (Modify an existing vehicle)
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

    // DELETE (Remove a vehicle by ID)
    public boolean delete(Long id) {
        if (database.containsKey(id)) {
            database.remove(id);
            return true;
        }
        return false;
    }
}