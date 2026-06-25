package com.gestionale.garage.web;

import com.gestionale.garage.model.FuelType;
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
        json.append("\"fuelType\":\"").append(vehicle.getFuelType()).append("\",");
        json.append("\"imageUrl\":");
        if (vehicle.getImageUrl() == null) {
            json.append("null");
        } else {
            json.append("\"").append(escape(vehicle.getImageUrl())).append("\"");
        }
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

    public static Vehicle fromJson(String json, Long id) {
        String make = readStringField(json, "make");
        String model = readStringField(json, "model");
        int year = readIntField(json, "year");
        double price = readDoubleField(json, "price");
        FuelType fuelType = readFuelTypeField(json, "fuelType");
        String imageUrl = readOptionalStringField(json, "imageUrl");
        return new Vehicle(id, make, model, year, price, fuelType, imageUrl);
    }

    public static String errorJson(String message) {
        return "{\"error\":\"" + escape(message) + "\"}";
    }

    public static String deletedJson() {
        return "{\"deleted\":true}";
    }

    private static String readOptionalStringField(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start < 0) {
            return null;
        }
        start += key.length();
        while (start < json.length() && Character.isWhitespace(json.charAt(start))) {
            start++;
        }
        if (start < json.length() && json.charAt(start) == 'n') {
            return null;
        }
        if (start >= json.length() || json.charAt(start) != '"') {
            throw new IllegalArgumentException("Invalid JSON for field: " + field);
        }
        start++;
        int end = json.indexOf('"', start);
        if (end < 0) {
            throw new IllegalArgumentException("Invalid JSON for field: " + field);
        }
        String value = unescape(json.substring(start, end));
        return value.isBlank() ? null : value;
    }

    private static String readStringField(String json, String field) {
        String key = "\"" + field + "\":\"";
        int start = json.indexOf(key);
        if (start < 0) {
            throw new IllegalArgumentException("Missing field: " + field);
        }
        start += key.length();
        int end = json.indexOf('"', start);
        if (end < 0) {
            throw new IllegalArgumentException("Invalid JSON for field: " + field);
        }
        return unescape(json.substring(start, end));
    }

    private static int readIntField(String json, String field) {
        return (int) readNumberField(json, field);
    }

    private static double readDoubleField(String json, String field) {
        return readNumberField(json, field);
    }

    private static double readNumberField(String json, String field) {
        String key = "\"" + field + "\":";
        int start = json.indexOf(key);
        if (start < 0) {
            throw new IllegalArgumentException("Missing field: " + field);
        }
        start += key.length();
        int end = start;
        while (end < json.length()) {
            char ch = json.charAt(end);
            if (ch == ',' || ch == '}' || ch == ']') {
                break;
            }
            end++;
        }
        try {
            return Double.parseDouble(json.substring(start, end).trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number for field: " + field);
        }
    }

    private static FuelType readFuelTypeField(String json, String field) {
        String value = readStringField(json, field);
        for (FuelType fuelType : FuelType.values()) {
            if (fuelType.name().equalsIgnoreCase(value)) {
                return fuelType;
            }
        }
        throw new IllegalArgumentException("Invalid fuelType: " + value);
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

    private static String unescape(String value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (ch == '\\' && i + 1 < value.length()) {
                char next = value.charAt(i + 1);
                if (next == '"') {
                    result.append('"');
                    i++;
                } else if (next == '\\') {
                    result.append('\\');
                    i++;
                } else if (next == 'n') {
                    result.append('\n');
                    i++;
                } else if (next == 'r') {
                    result.append('\r');
                    i++;
                } else if (next == 't') {
                    result.append('\t');
                    i++;
                } else {
                    result.append(ch);
                }
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
