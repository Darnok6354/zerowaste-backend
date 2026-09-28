package pl.konrad.zerowaste.dto;

import jakarta.validation.constraints.NotNull;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;

public record UpdateProductLifecycleRequest(
        @NotNull
        ProductLifecycleStatus lifecycleStatus,
        String wasteReason
) {
}