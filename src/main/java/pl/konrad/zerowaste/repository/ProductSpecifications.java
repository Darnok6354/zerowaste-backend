package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.domain.Specification;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;

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
        return (root, query, cb) ->
                name == null || name.isBlank()
                        ? null
                        : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            LocalDate today = LocalDate.now();
            LocalDate expiringSoonDate = today.plusDays(3);

            return switch (status) {
                case EXPIRED -> cb.and(
                        cb.isNotNull(root.get("expirationDate")),
                        cb.lessThan(root.get("expirationDate"), today)
                );

                case EXPIRING_SOON -> cb.and(
                        cb.isNotNull(root.get("expirationDate")),
                        cb.greaterThanOrEqualTo(root.get("expirationDate"), today),
                        cb.lessThanOrEqualTo(root.get("expirationDate"), expiringSoonDate)
                );

                case FRESH -> cb.or(
                        cb.isNull(root.get("expirationDate")),
                        cb.greaterThan(root.get("expirationDate"), expiringSoonDate)
                );
            };
        };
    }
}