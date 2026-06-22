package com.gestionale.garage.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Vehicle {
    private Long id;

    @NotBlank(message = "Make is required")
    private String make;

    @NotBlank(message = "Model is required")
    private String model;

    @Min(value = 1900, message = "Year cannot be before 1900")
    private int year;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    // Required by Jackson for JSON request bodies in Spring Boot
    public Vehicle() {
    }

    // Constructor with built-in validation
    public Vehicle(Long id, String make, String model, int year, double price, FuelType fuelType) {
        if (year < 1900) {
            throw new IllegalArgumentException("Year cannot be before 1900!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }

        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.fuelType = fuelType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1900) {
            throw new IllegalArgumentException("Year cannot be before 1900!");
        }
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative!");
        }
        this.price = price;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", fuelType=" + fuelType +
                '}';
    }
}
