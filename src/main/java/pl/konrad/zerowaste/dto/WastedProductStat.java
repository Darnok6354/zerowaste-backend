package pl.konrad.zerowaste.dto;

import pl.konrad.zerowaste.domain.ProductUnit;

// Pojedynczy słupek na wykresie: np. "Mleko - 2.0 LITERS"
public record WastedProductStat(
        String productName,
        Double totalQuantity,
        ProductUnit unit
) {
}