package pl.konrad.zerowaste.dto.spoonacular;

public record SpoonacularIngredientDto(
        Long id,
        String name,
        String original, // np. "2 cups of fresh spinach"
        Double amount,
        String unit
) {
}