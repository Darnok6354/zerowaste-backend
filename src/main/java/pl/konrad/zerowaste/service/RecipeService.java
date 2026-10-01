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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;
    private final RestClient restClient;
    private final String apiKey;

    // Wstrzykujemy zależności oraz zmienne ze środowiska (application.yml)
    // Zmieniony konstruktor (bez RestClient.Builder)
    public RecipeService(
            ProductRepository productRepository,
            CurrentUserService currentUserService,
            @Value("${spoonacular.api.url}") String apiUrl,
            @Value("${spoonacular.api.key}") String apiKey) {

        this.productRepository = productRepository;
        this.currentUserService = currentUserService;
        this.apiKey = apiKey;

        // NOWOŚĆ: Ręczne wywołanie statycznego buildera
        this.restClient = RestClient.builder().baseUrl(apiUrl).build();
        System.out.println("ZACIĄGNIĘTY KLUCZ API: " + apiKey);
    }

    public List<SpoonacularRecipeDto> getRecipesBasedOnInventory() {
        UserProfile currentUser = currentUserService.getCurrentUser();

        // 1. Priorytet #1: Pobieramy produkty z lodówki (ACTIVE), które zaraz się zepsują (EXPIRING_SOON)
        List<Product> expiringProducts = productRepository
                .findAllByUserProfile_IdAndLifecycleStatusAndStatus(
                        currentUser.getId(),
                        ProductLifecycleStatus.ACTIVE,
                        ProductStatus.EXPIRING_SOON
                );

        // --- DIAGNOSTYKA KROK 1: Czy baza coś zwraca? ---
        System.out.println("DEBUG: ID Użytkownika = " + currentUser.getId());
        System.out.println("DEBUG: Znaleziono EXPIRING = " + expiringProducts.size());

        List<Product> freshProducts = productRepository
                .findAllByUserProfile_IdAndLifecycleStatusAndStatus(
                        currentUser.getId(),
                        ProductLifecycleStatus.ACTIVE,
                        ProductStatus.FRESH
                );

        System.out.println("DEBUG: Znaleziono FRESH = " + freshProducts.size());

        String ingredientsParam;

        if (!expiringProducts.isEmpty()) {
            ingredientsParam = expiringProducts.stream()
                    .map(p -> p.getMasterProduct().getName())
                    .distinct()
                    .collect(Collectors.joining(","));
            // --- DIAGNOSTYKA KROK 2: Jaki string trafia do API? ---
            System.out.println("DEBUG: Wysyłam do API (EXPIRING) = " + ingredientsParam);
        }
        // 4. Jeśli nic się nie psuje, szukaj przepisów z normalnych zapasów
        else if (!freshProducts.isEmpty()) {
            ingredientsParam = freshProducts.stream()
                    .map(p -> p.getMasterProduct().getName())
                    .distinct()
                    .collect(Collectors.joining(","));
            System.out.println("DEBUG: Wysyłam do API (EXPIRING) = " + ingredientsParam);
        }
        // 5. Lodówka jest pusta (brak ACTIVE produktów)
        else {
            return List.of();
        }

        // 6. Wysyłamy zapytanie do API
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
                        // false, bo nie potrzebujemy dodatkowych danych o wartościach odżywczych
                        .queryParam("includeNutrition", false)
                        .build(recipeId))
                .header("x-api-key", apiKey)
                .retrieve()
                .body(SpoonacularRecipeDetailsDto.class);
    }
}