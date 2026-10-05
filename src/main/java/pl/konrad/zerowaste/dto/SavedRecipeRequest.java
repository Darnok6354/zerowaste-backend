package pl.konrad.zerowaste.dto;

public record SavedRecipeRequest(
        Long spoonacularRecipeId,
        String title,
        String imageUrl
) {}