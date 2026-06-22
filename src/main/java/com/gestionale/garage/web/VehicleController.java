package com.gestionale.garage.web;

import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleRepository repository;

    public VehicleController(VehicleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Vehicle> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        Vehicle vehicle = repository.findById(id);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle) {
        Vehicle saved = repository.add(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        Vehicle updated = repository.update(id, vehicle);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
        }
        return ResponseEntity.noContent().build();
    }
}
