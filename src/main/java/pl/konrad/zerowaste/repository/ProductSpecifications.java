package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.domain.Specification;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;

import java.time.LocalDate;

public class ProductSpecifications {

    public static Specification<Product> belongsToUser(Long userProfileId) {
        return (root, query, cb) ->
                userProfileId == null ? null : cb.equal(root.get("userProfile").get("id"), userProfileId);
    }

    public static Specification<Product> hasLocation(StorageLocation location) {
        return (root, query, cb) ->
                location == null ? null : cb.equal(root.get("location"), location);
    }

    public static Specification<Product> hasUnit(ProductUnit unit) {
        return (root, query, cb) ->
                unit == null ? null : cb.equal(root.get("unit"), unit);
    }

    public static Specification<Product> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            // POPRAWIONA LINIA:
            return cb.like(cb.lower(root.join("masterProduct").get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasStatus(List<ProductStatus> statuses) {
        return (root, query, cb) -> {
            if (statuses == null || statuses.isEmpty()) {
                return null; // Zwraca null, aby zignorować filtr, gdy nie ma statusów
            }

            LocalDate today = LocalDate.now();
            LocalDate expiringSoonDate = today.plusDays(3);

            // Lista na warunki dla poszczególnych statusów
            List<Predicate> statusPredicates = new ArrayList<>();

            for (ProductStatus status : statuses) {
                switch (status) {
                    case EXPIRED -> statusPredicates.add(cb.and(
                            cb.isNotNull(root.get("expirationDate")),
                            cb.lessThan(root.get("expirationDate"), today)
                    ));

                    case EXPIRING_SOON -> statusPredicates.add(cb.and(
                            cb.isNotNull(root.get("expirationDate")),
                            cb.greaterThanOrEqualTo(root.get("expirationDate"), today),
                            cb.lessThanOrEqualTo(root.get("expirationDate"), expiringSoonDate)
                    ));

                    case FRESH -> statusPredicates.add(cb.or(
                            cb.isNull(root.get("expirationDate")),
                            cb.greaterThan(root.get("expirationDate"), expiringSoonDate)
                    ));
                }
            }

            // Łączymy wszystkie zebrane warunki logicznym LUB (OR)
            return cb.or(statusPredicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> hasLifecycleStatus(ProductLifecycleStatus lifecycleStatus) {
        return (root, query, cb) ->
                lifecycleStatus == null ? null : cb.equal(root.get("lifecycleStatus"), lifecycleStatus);
    }
}