package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.*;
import pl.konrad.zerowaste.dto.ProductRequest;
import pl.konrad.zerowaste.dto.ProductResponse;
import pl.konrad.zerowaste.exception.ProductNotFoundException;
import pl.konrad.zerowaste.exception.ResourceNotFoundException;
import pl.konrad.zerowaste.repository.MasterProductRepository;
import pl.konrad.zerowaste.repository.ProductRepository;
import pl.konrad.zerowaste.repository.ProductSpecifications;
import pl.konrad.zerowaste.dto.ProductSummaryResponse;
import pl.konrad.zerowaste.dto.UpdateProductLifecycleRequest;
import pl.konrad.zerowaste.repository.ProductHistoryRepository;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MasterProductRepository masterProductRepository; // <-- Dodane repozytorium słownika
    private final CurrentUserService currentUserService;
    private final ProductHistoryRepository productHistoryRepository; // <-- DODANE

    public ProductResponse createProduct(ProductRequest request) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        // Pobranie produktu ze słownika na podstawie ID przesłanego z frontendu
        MasterProduct masterProduct = masterProductRepository.findById(request.masterProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Master product not found with ID: " + request.masterProductId()));

        Product product = Product.builder()
                .masterProduct(masterProduct) // Zastępuje dotychczasowe .name(request.name())
                .quantity(request.quantity())
                .unit(request.unit())
                .expirationDate(request.expirationDate())
                .location(request.location())
                .userProfile(currentUser)
                .build();

        return mapToResponse(productRepository.save(product));
    }

    public Page<ProductResponse> getAllProducts(
            String name,
            StorageLocation location,
            ProductUnit unit,
            ProductStatus status,
            String sortBy,
            String direction,
            int page,
            int size
    ) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        Specification<Product> specification = Specification
                .where(ProductSpecifications.belongsToUser(currentUser.getId()))
                .and(ProductSpecifications.hasLifecycleStatus(ProductLifecycleStatus.ACTIVE))
                .and(ProductSpecifications.nameContains(name)) // <-- Wymaga małej zmiany w ProductSpecifications (wyjaśnienie poniżej)
                .and(ProductSpecifications.hasLocation(location))
                .and(ProductSpecifications.hasUnit(unit))
                .and(ProductSpecifications.hasStatus(status));

        Pageable pageable = PageRequest.of(page, size, buildSort(sortBy, direction));

        return productRepository.findAll(specification, pageable)
                .map(this::mapToResponse);
    }

    public ProductResponse getProductById(Long id) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        Product product = productRepository.findByIdAndUserProfile_Id(id, currentUser.getId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        return mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        Product product = productRepository.findByIdAndUserProfile_Id(id, currentUser.getId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        // Aktualizacja powiązania ze słownikiem (jeśli użytkownik zmienił typ produktu)
        if (!product.getMasterProduct().getId().equals(request.masterProductId())) {
            MasterProduct newMasterProduct = masterProductRepository.findById(request.masterProductId())
                    .orElseThrow(() -> new RuntimeException("Master product not found with ID: " + request.masterProductId()));
            product.setMasterProduct(newMasterProduct);
        }

        product.setQuantity(request.quantity());
        product.setUnit(request.unit());
        product.setExpirationDate(request.expirationDate());
        product.setLocation(request.location());

        return mapToResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        Product product = productRepository.findByIdAndUserProfile_Id(id, currentUser.getId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }

    private ProductResponse mapToResponse(Product product) {
        // Zaktualizowane mapowanie uwzględniające dane z encji MasterProduct
        return new ProductResponse(
                product.getId(),
                product.getMasterProduct().getId(),
                product.getMasterProduct().getName(),
                product.getMasterProduct().getCategory(),
                product.getMasterProduct().getImageUrl(),
                product.getQuantity(),
                product.getUnit(),
                product.getExpirationDate(),
                product.getLocation(),
                calculateStatus(product),
                product.getLifecycleStatus(),
                product.getWasteReason(),
                product.getStatusChangedAt(),
                product.getCreatedAt()
        );
    }

    private ProductStatus calculateStatus(Product product) {
        if (product.getExpirationDate() == null) {
            return ProductStatus.FRESH;
        }

        LocalDate today = LocalDate.now();
        LocalDate expirationDate = product.getExpirationDate();

        if (expirationDate.isBefore(today)) {
            return ProductStatus.EXPIRED;
        }

        if (!expirationDate.isAfter(today.plusDays(3))) {
            return ProductStatus.EXPIRING_SOON;
        }

        return ProductStatus.FRESH;
    }

    private Sort buildSort(String sortBy, String direction) {
        String sortField = switch (sortBy == null ? "" : sortBy) {
            case "name" -> "masterProduct.name"; // <-- To jest kluczowe przy sortowaniu
            case "quantity" -> "quantity";
            case "expirationDate" -> "expirationDate";
            case "location" -> "location";
            default -> "createdAt";
        };

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(sortDirection, sortField);
    }

    public ProductSummaryResponse getProductsSummary() {
        // Kod pozostaje bez zmian
        UserProfile currentUser = currentUserService.getCurrentUser();

        LocalDate today = LocalDate.now();
        LocalDate expiringSoonDate = today.plusDays(3);

        long total = productRepository.countByUserProfile_Id(currentUser.getId());
        long expired = productRepository.countByUserProfile_IdAndExpirationDateBefore(currentUser.getId(), today);
        long expiringSoon = productRepository.countByUserProfile_IdAndExpirationDateBetween(
                currentUser.getId(),
                today,
                expiringSoonDate
        );
        long freshWithDate = productRepository.countByUserProfile_IdAndExpirationDateAfter(currentUser.getId(), expiringSoonDate);
        long freshWithoutDate = productRepository.countByUserProfile_IdAndExpirationDateIsNull(currentUser.getId());

        long fresh = freshWithDate + freshWithoutDate;

        return new ProductSummaryResponse(
                total,
                fresh,
                expiringSoon,
                expired
        );
    }

    public ProductResponse updateProductLifecycle(Long id, UpdateProductLifecycleRequest request) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        Product product = productRepository.findByIdAndUserProfile_Id(id, currentUser.getId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setLifecycleStatus(request.lifecycleStatus());
        product.setStatusChangedAt(LocalDateTime.now());

        if (request.lifecycleStatus() == ProductLifecycleStatus.WASTED) {
            product.setWasteReason(request.wasteReason());
        } else {
            product.setWasteReason(null);
        }

        // Zapisz zaktualizowany produkt w głównej tabeli
        Product updatedProduct = productRepository.save(product);

        // --- NOWOŚĆ: Zapisz do historii, jeśli produkt został zjedzony lub wyrzucony ---
        if (request.lifecycleStatus() == ProductLifecycleStatus.CONSUMED ||
                request.lifecycleStatus() == ProductLifecycleStatus.WASTED) {

            ProductHistory historyRecord = ProductHistory.builder()
                    .userProfile(currentUser)
                    .masterProduct(updatedProduct.getMasterProduct())
                    .lifecycleStatus(request.lifecycleStatus())
                    .wasteReason(updatedProduct.getWasteReason())
                    .quantity(updatedProduct.getQuantity())
                    .unit(updatedProduct.getUnit())
                    .timestamp(LocalDateTime.now())
                    .build();

            productHistoryRepository.save(historyRecord);
        }

        return mapToResponse(updatedProduct);
    }
}