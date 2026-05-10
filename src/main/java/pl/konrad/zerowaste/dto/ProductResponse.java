
package pl.konrad.zerowaste.dto;

import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal quantity,
        ProductUnit unit,
        LocalDate expirationDate,
        StorageLocation location,
        ProductStatus status,
        LocalDateTime createdAt
) {
}