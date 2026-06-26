# Garage Manager

A simple web app to manage vehicles in a garage. Built with Java, Spring Boot, and an H2 database.

## Requirements

- Java 21
- Maven (included via `mvnw` wrapper, or install Maven separately)

## How to run

From the project root:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
.\mvnw.cmd spring-boot:run
```

The app starts at `http://localhost:8080`.

## API

Base path: `/api/vehicles`

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/vehicles` | List all vehicles (optional filters: `make`, `model`, `year`, `price`, `fuelType`) |
| GET | `/api/vehicles/{id}` | Get one vehicle by id |
| POST | `/api/vehicles` | Create a new vehicle |
| PUT | `/api/vehicles/{id}` | Update an existing vehicle |
| DELETE | `/api/vehicles/{id}` | Delete a vehicle |

Example JSON body (`imageUrl` is optional):

```json
{
  "make": "Fiat",
  "model": "500",
  "year": 2020,
  "price": 15000,
  "fuelType": "HYBRID",
  "imageUrl": "https://example.com/fiat-500.jpg"
}
```

Allowed `fuelType` values: `GASOLINE`, `DIESEL`, `ELECTRIC`, `HYBRID`, `LPG`.

## Database

The app uses an H2 file database stored in `data/`.

On first startup, `VehicleDataSeeder` inserts two sample vehicles (Fiat 500 and Tesla Model 3).

### H2 console

1. Start the app.
2. Open `http://localhost:8080/h2-console`.
3. Connect with:

| Field | Value |
|-------|-------|
| JDBC URL | `jdbc:h2:file:./data/garage;AUTO_SERVER=TRUE` |
| User Name | `sa` |
| Password | *(leave empty)* |

Useful query:

```sql
SELECT id, make, model, vehicle_year, price, fuel_type, image_url FROM vehicle;
```

### `vehicle` table columns

| Column | Meaning |
|--------|---------|
| `id` | Auto-generated primary key |
| `make` | Brand |
| `model` | Model name |
| `vehicle_year` | Year |
| `price` | Price |
| `fuel_type` | Fuel type |
| `image_url` | Optional image URL |

## Tests

Unit tests live under `src/test/java/` and use Mockito to test `VehicleService` without starting Spring Boot or H2.

Run all tests:

```bash
./mvnw test
```

On Windows:

```bash
.\mvnw.cmd test
```

## Project structure

```
src/main/java/com/gestionale/garage/
  model/        Vehicle, FuelType
  repository/   VehicleRepository (database access)
  service/      VehicleService (business logic)
  web/          VehicleController (HTTP API)
src/main/resources/static/
  index.html    Web UI
```
