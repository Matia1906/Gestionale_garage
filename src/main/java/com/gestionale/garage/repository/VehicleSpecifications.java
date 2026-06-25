package com.gestionale.garage.repository;

import com.gestionale.garage.model.Vehicle;
import com.gestionale.garage.service.VehicleFilter;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class VehicleSpecifications {

    private VehicleSpecifications() {
    }

    public static Specification<Vehicle> matches(VehicleFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.make() != null && !filter.make().isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("make")),
                        filter.make().trim().toLowerCase()));
            }

            if (filter.model() != null && !filter.model().isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("model")),
                        filter.model().trim().toLowerCase()));
            }

            if (filter.year() != null) {
                predicates.add(criteriaBuilder.equal(root.get("year"), filter.year()));
            }

            if (filter.price() != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), filter.price()));
            }

            if (filter.fuelType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("fuelType"), filter.fuelType()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
