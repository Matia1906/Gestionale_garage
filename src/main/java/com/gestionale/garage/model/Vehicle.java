package com.gestionale.garage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is required")
    @Column(nullable = false)
    private String make;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @Min(value = 1900, message = "Year cannot be before 1900")
    @Column(name = "vehicle_year", nullable = false)
    private int year;

    @Min(value = 0, message = "Price cannot be negative")
    @Column(nullable = false)
    private double price;

    @NotNull(message = "Fuel type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @URL(message = "Image URL must be a valid http or https URL")
    @Column(name = "image_url")
    private String imageUrl;

    public Vehicle() {
    }

    public Vehicle(Long id, String make, String model, int year, double price, FuelType fuelType) {
        this(id, make, model, year, price, fuelType, null);
    }

    public Vehicle(Long id, String make, String model, int year, double price, FuelType fuelType, String imageUrl) {
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
        setImageUrl(imageUrl);
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.isBlank()) {
            this.imageUrl = null;
        } else {
            this.imageUrl = imageUrl;
        }
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
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
