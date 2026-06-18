package com.gestionale.garage;

import com.gestionale.garage.model.FuelType;
import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import com.gestionale.garage.web.VehicleHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class VehicleHttpServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        VehicleRepository repository = seedRepository();

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/vehicles", new VehicleHandler(repository));
        server.setExecutor(null);
        server.start();

        System.out.println("Server running on http://localhost:" + PORT);
        System.out.println("Try: http://localhost:" + PORT + "/api/vehicles");
        Thread.currentThread().join();
    }

    private static VehicleRepository seedRepository() {
        VehicleRepository repository = new VehicleRepository();

        repository.add(new Vehicle(0L, "Fiat", "500", 2020, 15000, FuelType.HYBRID));
        repository.add(new Vehicle(0L, "Tesla", "Model 3", 2022, 45000, FuelType.ELECTRIC));

        return repository;
    }
}
