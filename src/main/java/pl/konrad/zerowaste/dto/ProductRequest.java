package pl.konrad.zerowaste.dto;

import jakarta.validation.constraints.NotNull;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;
import java.time.LocalDate;

public record ProductRequest(
        @NotNull(message = "Master product ID cannot be null")
        Long masterProductId, // Zastępuje pole 'name'

        @NotNull
        Double quantity,

        @NotNull
        ProductUnit unit,

        LocalDate expirationDate,

        @NotNull
        StorageLocation location // Używamy 'location' zgodnie z Twoim wcześniejszym kodem
) {
}