package com.gestionale.garage.web;

import com.gestionale.garage.model.Vehicle;
import java.util.List;

public final class VehicleJson {

    private VehicleJson() {
    }

    public static String toJson(Vehicle vehicle) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"id\":").append(vehicle.getId()).append(",");
        json.append("\"make\":\"").append(escape(vehicle.getMake())).append("\",");
        json.append("\"model\":\"").append(escape(vehicle.getModel())).append("\",");
        json.append("\"year\":").append(vehicle.getYear()).append(",");
        json.append("\"price\":").append(vehicle.getPrice()).append(",");
        json.append("\"fuelType\":\"").append(vehicle.getFuelType()).append("\"");
        json.append("}");
        return json.toString();
    }

    public static String toJson(List<Vehicle> vehicles) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < vehicles.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(toJson(vehicles.get(i)));
        }
        json.append("]");
        return json.toString();
    }

    public static String errorJson(String message) {
        return "{\"error\":\"" + escape(message) + "\"}";
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
