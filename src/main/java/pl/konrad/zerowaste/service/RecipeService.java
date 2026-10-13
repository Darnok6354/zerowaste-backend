package pl.konrad.zerowaste.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.dto.spoonacular.SpoonacularRecipeDto;
import pl.konrad.zerowaste.repository.ProductRepository;
import pl.konrad.zerowaste.dto.spoonacular.SpoonacularRecipeDetailsDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;
    private final RestClient restClient;
    private final String apiKey;

    public RecipeService(
            ProductRepository productRepository,
            CurrentUserService currentUserService,
            @Value("${spoonacular.api.url}") String apiUrl,
            @Value("${spoonacular.api.key}") String apiKey) {

        this.productRepository = productRepository;
        this.currentUserService = currentUserService;
        this.apiKey = apiKey;
        this.restClient = RestClient.builder().baseUrl(apiUrl).build();
    }

    public List<SpoonacularRecipeDto> getRecipesBasedOnInventory() {
        UserProfile currentUser = currentUserService.getCurrentUser();

        LocalDate today = LocalDate.now();
        LocalDate expirationThreshold = today.plusDays(2);

        // 1. Priorytet #1: Pobieramy produkty z lodówki (ACTIVE), które zaraz się zepsują (od dziś do +2 dni)
        // Ignorujemy produkty już przeterminowane (data < today), żeby nie dodawać zepsutego jedzenia do przepisów
        List<Product> expiringProducts = productRepository
                .findByUserProfile_IdAndLifecycleStatusAndExpirationDateBetween(
                        currentUser.getId(),
                        ProductLifecycleStatus.ACTIVE,
                        today,
                        expirationThreshold
                );

        String ingredientsParam;

        if (!expiringProducts.isEmpty()) {
            ingredientsParam = expiringProducts.stream()
                    .map(p -> p.getMasterProduct().getName())
                    .distinct()
                    .collect(Collectors.joining(","));
        }
        else {
            // 2. Jeśli nic się nie psuje, szukaj przepisów ze świeżych zapasów (data > today + 2 LUB brak daty)
            List<Product> freshProducts = productRepository
                    .findFreshActiveProducts(
                            currentUser.getId(),
                            ProductLifecycleStatus.ACTIVE,
                            expirationThreshold
                    );

            if (!freshProducts.isEmpty()) {
                ingredientsParam = freshProducts.stream()
                        .map(p -> p.getMasterProduct().getName())
                        .distinct()
                        .collect(Collectors.joining(","));
            } else {
                // 3. Lodówka jest pusta (brak ACTIVE produktów)
                return List.of();
            }
        }

        // 4. Wysyłamy zapytanie do API
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipes/findByIngredients")
                        .queryParam("ingredients", ingredientsParam)
                        .queryParam("number", 5)
                        .queryParam("ranking", 2)
                        .build())
                .header("x-api-key", apiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<List<SpoonacularRecipeDto>>() {});
    }

    public SpoonacularRecipeDetailsDto getRecipeDetails(Long recipeId) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipes/{id}/information")
                        .queryParam("includeNutrition", false)
                        .build(recipeId))
                .header("x-api-key", apiKey)
                .retrieve()
                .body(SpoonacularRecipeDetailsDto.class);
    }
}