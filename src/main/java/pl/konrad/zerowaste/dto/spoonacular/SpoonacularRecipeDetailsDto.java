package pl.konrad.zerowaste.dto.spoonacular;

public record SpoonacularRecipeDetailsDto(
        Long id,
        String title,
        String image,
        Integer readyInMinutes, // Czas przygotowania
        Integer servings,       // Liczba porcji
        String sourceUrl,       // Link do oryginalnego przepisu
        String instructions     // Instrukcja gotowania (często w formacie HTML)
) {
}