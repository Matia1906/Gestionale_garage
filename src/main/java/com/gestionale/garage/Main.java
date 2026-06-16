public class Main {
    public static void main(String[] args) {
        VehicleRepository repo = new VehicleRepository();

        System.out.println("--- Garage Management Test Suite ---");

        // 1. Test Add (Seeded samples)
        // We pass 0L as temporary ID since the repository will overwrite it
        Vehicle v1 = new Vehicle(0L, "Fiat", "500", 2020, 15000, FuelType.HYBRID);
        Vehicle v2 = new Vehicle(0L, "Tesla", "Model 3", 2022, 45000, FuelType.ELECTRIC);
        
        Vehicle saved1 = repo.add(v1);
        Vehicle saved2 = repo.add(v2);

        // 2. Test Find All
        System.out.println("\n--- All vehicles in the garage: ---");
        repo.findAll().forEach(System.out.println);

        // 3. Test Find By ID
        System.out.println("\n--- Searching for vehicle with ID 1: ---");
        System.out.println(repo.findById(1L));

        // 4. Test Update
        System.out.println("\n--- Updating price for Fiat 500 (ID 1) ---");
        Vehicle updatedVehicle = new Vehicle(1L, "Fiat", "500", 2020, 13500, FuelType.HYBRID);
        repo.update(1L, updatedVehicle);
        System.out.println("After update: " + repo.findById(1L));

        // 5. Test Delete
        System.out.println("\n--- Deleting Tesla (ID 2) ---");
        repo.delete(2L);
        System.out.println("Remaining vehicles count: " + repo.findAll().size());
        
        // 6. Test Validation Rules
        System.out.println("\n--- Testing validation with invalid year (Should throw exception) ---");
        try {
            Vehicle invalidVehicle = new Vehicle(0L, "Ford", "Model T", 1850, 5000, FuelType.GASOLINE);
        } catch (IllegalArgumentException e) {
            System.out.println("Success! Exception caught: " + e.getMessage());
        }
    }
}