package pl.konrad.zerowaste.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.StorageLocation;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.dto.*;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.repository.ProductRepository;
import pl.konrad.zerowaste.service.CurrentUserService;
import pl.konrad.zerowaste.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService productService;

    // DODANE ZALEŻNOŚCI POTRZEBNE DO NOWEJ FUNKCJI
    private final CurrentUserService currentUserService;
    private final ProductRepository productRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) StorageLocation location,
            @RequestParam(required = false) ProductUnit unit,
            @RequestParam(required = false) List<ProductStatus> statuses,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return productService.getAllProducts(
                name,
                location,
                unit,
                statuses,
                sortBy,
                direction,
                page,
                size
        );
    }

    @GetMapping("/summary")
    public ProductSummaryResponse getProductsSummary() {
        return productService.getProductsSummary();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("/{id}/lifecycle")
    public ProductResponse updateProductLifecycle(@PathVariable Long id,
                                                  @Valid @RequestBody UpdateProductLifecycleRequest request) {
        return productService.updateProductLifecycle(id, request);
    }

    // STARA METODA (zostaje bez zmian)
    @PostMapping("/cook")
    public ResponseEntity<Void> cookRecipe(@RequestBody CookRecipeRequest request) {
        productService.cookRecipe(request);
        return ResponseEntity.ok().build();
    }

    // NOWA METODA DLA GENEROWANYCH PRZEPISÓW
    @PostMapping("/cook-suggested")
    public ResponseEntity<Void> cookFromRecipe(@RequestBody CookByRecipeRequest request) {
        UserProfile user = currentUserService.getCurrentUser();

        List<ConsumedIngredientRequest> consumedIngredients = new ArrayList<>();

        System.out.println("=== ROZPOCZYNAMY GOTOWANIE ==="); // DEBUG

        for (var ingredient : request.ingredients()) {
            System.out.println("Spoonacular chce zużyć: " + ingredient.name() + " w ilości: " + ingredient.amount());

            // ZMIANA TUTAJ: Używamy nowej metody z odwróconym dopasowaniem
            List<Product> matchedProducts = productRepository.findProductsMatchingSpoonacularName(
                    user.getId(),
                    ingredient.name(),
                    ProductLifecycleStatus.ACTIVE
            );

            if (!matchedProducts.isEmpty()) {
                // Bierzemy pierwszy produkt, który pasuje do zapytania
                Product p = matchedProducts.get(0);
                System.out.println(" -> ZNALEZIONO W LODÓWCE! ID: " + p.getId() + " (Dopasowano do: " + p.getMasterProduct().getName() + ")");

                consumedIngredients.add(new ConsumedIngredientRequest(
                        p.getId(),
                        ingredient.amount() > 0 ? ingredient.amount() : 1.0
                ));
            } else {
                System.out.println(" -> NIE ZNALEZIONO w lodówce produktu pasującego do: " + ingredient.name());
            }
        }

        System.out.println("Do aktualizacji przekazuję " + consumedIngredients.size() + " produktów."); // DEBUG

        if (!consumedIngredients.isEmpty()) {
            productService.cookRecipe(new CookRecipeRequest(null, consumedIngredients));
            System.out.println("Sukces: Wywołano productService.cookRecipe!"); // DEBUG
        } else {
            System.out.println("Ostrzeżenie: Lista do zużycia jest pusta. Nic nie zaktualizowano."); // DEBUG
        }

        return ResponseEntity.noContent().build();
    }
}