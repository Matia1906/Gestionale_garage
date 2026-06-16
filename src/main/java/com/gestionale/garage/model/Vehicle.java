public class Vehicle {
    private Long id;
    private String make;
    private String model;
    private int year;
    private double price;
    private FuelType fuelType;

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

    // toString method for easy debugging and console printing
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