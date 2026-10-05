package pl.konrad.zerowaste.dto;

import java.time.LocalDateTime;

public record SavedRecipeResponse(
        Long id,
        Long spoonacularRecipeId,
        String title,
        String imageUrl,
        LocalDateTime savedAt
) {}