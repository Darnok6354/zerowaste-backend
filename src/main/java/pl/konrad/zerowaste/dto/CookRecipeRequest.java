package pl.konrad.zerowaste.dto;

import java.util.List;

public record CookRecipeRequest(
        Long spoonacularRecipeId, // Opcjonalnie, jeśli w przyszłości zrobisz tabelę z historią ugotowanych dań
        List<ConsumedIngredientRequest> consumedIngredients
) {}