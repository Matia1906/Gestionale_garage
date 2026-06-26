package com.gestionale.garage.service;

import com.gestionale.garage.model.FuelType;
import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.repository.VehicleRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository repository;

    @InjectMocks
    private VehicleService service;

    @Test
    void findById_returnsVehicle_whenIdExists() {
        Vehicle fiat = new Vehicle(1L, "Fiat", "500", 2020, 15000, FuelType.HYBRID);
        when(repository.findById(1L)).thenReturn(Optional.of(fiat));

        Vehicle result = service.findById(1L);

        assertEquals("Fiat", result.getMake());
        assertEquals("500", result.getModel());
    }

    @Test
    void findById_throwsNotFound_whenIdDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class,
                () -> service.findById(99L)
        );
        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
    }

    @Test
    void create_clearsIdBeforeSave() {
        Vehicle input = new Vehicle(99L, "Tesla", "Model 3", 2022, 45000, FuelType.ELECTRIC);
        Vehicle saved = new Vehicle(1L, "Tesla", "Model 3", 2022, 45000, FuelType.ELECTRIC);
        when(repository.save(any(Vehicle.class))).thenReturn(saved);

        Vehicle result = service.create(input);

        assertEquals(1L, result.getId());
        verify(repository).save(argThat(vehicle -> vehicle.getId() == null));
    }

    @Test
    void delete_throwsNotFound_whenIdDoesNotExist() {
        when(repository.existsById(5L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.delete(5L));
        verify(repository, never()).deleteById(5L);
    }

    @Test
    void findAll_returnsAllVehicles_whenFilterIsEmpty() {
        List<Vehicle> vehicles = List.of(
                new Vehicle(1L, "Fiat", "500", 2020, 15000, FuelType.HYBRID)
        );
        when(repository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = service.findAll(new VehicleFilter(null, null, null, null, null));

        assertEquals(1, result.size());
        assertEquals("Fiat", result.get(0).getMake());
        verify(repository).findAll();
    }

    @Test
    void update_copiesImageUrl_whenUpdatingVehicle() {
        Vehicle existing = new Vehicle(1L, "Fiat", "500", 2020, 15000, FuelType.HYBRID);
        Vehicle updates = new Vehicle(
                null,
                "Fiat",
                "500",
                2020,
                15000,
                FuelType.HYBRID,
                "https://example.com/fiat-500.jpg"
        );
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicle result = service.update(1L, updates);

        assertEquals("https://example.com/fiat-500.jpg", result.getImageUrl());
    }
}
