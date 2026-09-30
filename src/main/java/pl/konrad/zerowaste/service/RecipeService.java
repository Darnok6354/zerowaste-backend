package pl.konrad.zerowaste.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
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

        // 1. Pobieramy z lodówki tylko "AKTYWNE" produkty
        List<Product> userProducts = productRepository
                .findAllByUserProfile_IdAndLifecycleStatus(currentUser.getId(), ProductLifecycleStatus.ACTIVE);

        // 2. Wyciągamy angielskie nazwy ze słownika i łączymy je przecinkami (np. "milk,egg,apple")
        String ingredientsParam = userProducts.stream()
                .map(p -> p.getMasterProduct().getName())
                .distinct() // Pomijamy powtórzenia (np. jeśli użytkownik ma 2x mleko)
                .collect(Collectors.joining(","));

        // Jeśli lodówka jest pusta, nie wysyłamy zapytania do API, żeby nie marnować limitu
        if (ingredientsParam.isBlank()) {
            return List.of();
        }

        // 3. Budujemy i wysyłamy request do API Spoonacular
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipes/findByIngredients")
                        .queryParam("ingredients", ingredientsParam)
                        .queryParam("number", 1)
                        .queryParam("ranking", 2)
                        // USUNĘLIŚMY STĄD .queryParam("apiKey", apiKey)
                        .build())
                .header("x-api-key", apiKey) // <-- DODANE: Przekazujemy klucz bezpiecznie w nagłówku
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