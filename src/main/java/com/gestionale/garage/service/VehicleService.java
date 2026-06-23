package com.gestionale.garage.service;

import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import com.gestionale.garage.repository.VehicleSpecifications;
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

    public List<Vehicle> findAll(VehicleFilter filter) {
        if (filter == null || isEmpty(filter)) {
            return repository.findAll();
        }
        return repository.findAll(VehicleSpecifications.matches(filter));
    }

    public Vehicle findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    public Vehicle create(Vehicle vehicle) {
        vehicle.setId(null);
        return repository.save(vehicle);
    }

    public Vehicle update(Long id, Vehicle updates) {
        Vehicle existing = findById(id);
        existing.setMake(updates.getMake());
        existing.setModel(updates.getModel());
        existing.setYear(updates.getYear());
        existing.setPrice(updates.getPrice());
        existing.setFuelType(updates.getFuelType());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        repository.deleteById(id);
    }

    private boolean isEmpty(VehicleFilter filter) {
        return (filter.make() == null || filter.make().isBlank())
                && (filter.model() == null || filter.model().isBlank())
                && filter.year() == null
                && filter.price() == null
                && filter.fuelType() == null;
    }
}
