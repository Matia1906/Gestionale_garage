package com.gestionale.garage;

import com.gestionale.garage.model.FuelType;
import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GarageApplication implements CommandLineRunner {

    private final VehicleRepository repository;

    public GarageApplication(VehicleRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(GarageApplication.class, args);
    }

    @Override
    public void run(String... args) {
        seedRepository();
    }

    private void seedRepository() {
        repository.add(new Vehicle(0L, "Fiat", "500", 2020, 15000, FuelType.HYBRID));
        repository.add(new Vehicle(0L, "Tesla", "Model 3", 2022, 45000, FuelType.ELECTRIC));
    }
}
