package pl.konrad.zerowaste.dto.spoonacular;

import java.util.List;

public record SpoonacularRecipeDto(
        Long id,
        String title,
        String image,
        Integer usedIngredientCount,
        Integer missedIngredientCount,
        List<SpoonacularIngredientDto> usedIngredients,
        List<SpoonacularIngredientDto> missedIngredients
) {
}