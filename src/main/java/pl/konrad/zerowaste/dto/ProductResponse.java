package pl.konrad.zerowaste.dto;

import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,

        // Nowe pola pochodzące ze słownika (MasterProduct)
        Long masterProductId,
        String name,
        String category,
        String imageUrl,

        // Twoje dotychczasowe pola dla konkretnego produktu
        Double quantity,
        ProductUnit unit,
        LocalDate expirationDate,
        StorageLocation location,
        ProductStatus status,
        ProductLifecycleStatus lifecycleStatus,
        String wasteReason,
        LocalDateTime statusChangedAt,
        LocalDateTime createdAt
) {
}