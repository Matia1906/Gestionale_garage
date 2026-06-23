package com.gestionale.garage.service;

import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    public Vehicle findById(Long id) {
        Vehicle vehicle = repository.findById(id);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return vehicle;
    }

    public Vehicle create(Vehicle vehicle) {
        return repository.add(vehicle);
    }

    public Vehicle update(Long id, Vehicle vehicle) {
        Vehicle updated = repository.update(id, vehicle);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return updated;
    }

    public void delete(Long id) {
        if (!repository.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
    }
}
