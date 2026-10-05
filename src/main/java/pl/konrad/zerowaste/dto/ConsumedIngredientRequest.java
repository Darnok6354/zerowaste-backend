package pl.konrad.zerowaste.dto;

public record ConsumedIngredientRequest(
        Long productId,
        Double quantityUsed // Używamy Double na wypadek ułamków (np. 0.5 litra mleka)
) {}