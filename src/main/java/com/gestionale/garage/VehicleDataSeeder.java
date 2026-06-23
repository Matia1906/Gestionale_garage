package com.gestionale.garage;

import com.gestionale.garage.model.FuelType;
import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VehicleDataSeeder implements CommandLineRunner {

    private final VehicleRepository repository;

    public VehicleDataSeeder(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.save(new Vehicle(null, "Fiat", "500", 2020, 15000, FuelType.HYBRID));
        repository.save(new Vehicle(null, "Tesla", "Model 3", 2022, 45000, FuelType.ELECTRIC));
    }
}
