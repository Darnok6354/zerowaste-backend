package pl.konrad.zerowaste.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
        BigDecimal quantity,

        @NotNull(message = "Unit is required")
        ProductUnit unit,

        LocalDate expirationDate,

        @NotNull(message = "Location is required")
        StorageLocation location
) {
}