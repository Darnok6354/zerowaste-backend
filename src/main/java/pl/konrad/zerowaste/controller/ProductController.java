package pl.konrad.zerowaste.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;
import pl.konrad.zerowaste.dto.*;
import pl.konrad.zerowaste.service.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication") // <-- Musi być identyczna jak wyżej!
public class ProductController {

    private final ProductService productService;

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

    @PostMapping("/cook")
    public ResponseEntity<Void> cookRecipe(@RequestBody CookRecipeRequest request) {
        productService.cookRecipe(request);
        return ResponseEntity.ok().build();
    }


}