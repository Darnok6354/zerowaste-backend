package pl.konrad.zerowaste.dto;

import java.util.List;

public record CookByRecipeRequest(
        List<RecipeIngredientDto> ingredients
) {
    public record RecipeIngredientDto(String name, double amount) {}
}