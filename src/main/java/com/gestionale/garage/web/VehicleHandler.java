package com.gestionale.garage.web;

import com.gestionale.garage.model.FuelType;
import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleHandler implements HttpHandler {

    private static final String BASE_PATH = "/api/vehicles";

    private final VehicleRepository repository;

    public VehicleHandler(VehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (!"GET".equals(exchange.getRequestMethod())) {
                sendJson(exchange, 405, VehicleJson.errorJson("Method not allowed"));
                return;
            }

            URI requestUri = exchange.getRequestURI();
            String path = requestUri.getPath();
            String remainder = path.substring(BASE_PATH.length());

            if (remainder.isEmpty() || "/".equals(remainder)) {
                handleList(exchange, requestUri.getQuery());
                return;
            }

            if (remainder.startsWith("/")) {
                handleById(exchange, remainder.substring(1));
                return;
            }

            sendJson(exchange, 404, VehicleJson.errorJson("Route not found"));
        } finally {
            exchange.close();
        }
    }

    private void handleList(HttpExchange exchange, String query) throws IOException {
        String makeFilter = parseQueryParam(query, "make");
        String modelFilter = parseQueryParam(query, "model");
        String yearRaw = parseQueryParam(query, "year");
        String priceRaw = parseQueryParam(query, "price");
        String fuelTypeRaw = parseQueryParam(query, "fuelType");

        Integer yearFilter = null;
        if (yearRaw != null) {
            try {
                yearFilter = Integer.parseInt(yearRaw);
            } catch (NumberFormatException e) {
                sendJson(exchange, 400, VehicleJson.errorJson("Invalid year filter"));
                return;
            }
        }

        Double priceFilter = null;
        if (priceRaw != null) {
            try {
                priceFilter = Double.parseDouble(priceRaw);
            } catch (NumberFormatException e) {
                sendJson(exchange, 400, VehicleJson.errorJson("Invalid price filter"));
                return;
            }
        }

        FuelType fuelTypeFilter = null;
        if (fuelTypeRaw != null) {
            fuelTypeFilter = parseFuelType(fuelTypeRaw);
            if (fuelTypeFilter == null) {
                sendJson(exchange, 400, VehicleJson.errorJson("Invalid fuelType filter"));
                return;
            }
        }

        final Integer year = yearFilter;
        final Double price = priceFilter;
        final FuelType fuelType = fuelTypeFilter;

        List<Vehicle> vehicles = repository.findAll().stream()
                .filter(vehicle -> makeFilter == null || vehicle.getMake().equalsIgnoreCase(makeFilter))
                .filter(vehicle -> modelFilter == null || vehicle.getModel().equalsIgnoreCase(modelFilter))
                .filter(vehicle -> year == null || vehicle.getYear() == year)
                .filter(vehicle -> price == null || vehicle.getPrice() == price)
                .filter(vehicle -> fuelType == null || vehicle.getFuelType() == fuelType)
                .collect(Collectors.toList());

        sendJson(exchange, 200, VehicleJson.toJson(vehicles));
    }

    private void handleById(HttpExchange exchange, String idSegment) throws IOException {
        try {
            Long id = Long.parseLong(idSegment);
            Vehicle vehicle = repository.findById(id);

            if (vehicle == null) {
                sendJson(exchange, 404, VehicleJson.errorJson("Vehicle not found"));
                return;
            }

            sendJson(exchange, 200, VehicleJson.toJson(vehicle));
        } catch (NumberFormatException e) {
            sendJson(exchange, 400, VehicleJson.errorJson("Invalid vehicle id"));
        }
    }

    private String parseQueryParam(String query, String name) {
        if (query == null || query.isBlank()) {
            return null;
        }

        String prefix = name + "=";
        for (String param : query.split("&")) {
            if (param.startsWith(prefix)) {
                String value = param.substring(prefix.length());
                if (value.isBlank()) {
                    return null;
                }
                return value;
            }
        }

        return null;
    }

    private FuelType parseFuelType(String value) {
        for (FuelType fuelType : FuelType.values()) {
            if (fuelType.name().equalsIgnoreCase(value)) {
                return fuelType;
            }
        }
        return null;
    }

    private void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] body = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, body.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(body);
        }
    }
}
