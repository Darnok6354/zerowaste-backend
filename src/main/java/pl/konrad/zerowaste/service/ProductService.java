package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.*;
import pl.konrad.zerowaste.dto.ProductRequest;
import pl.konrad.zerowaste.dto.ProductResponse;
import pl.konrad.zerowaste.exception.ProductNotFoundException;
import pl.konrad.zerowaste.repository.ProductRepository;
import pl.konrad.zerowaste.repository.ProductSpecifications;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;

    public ProductResponse createProduct(ProductRequest request) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        Product product = Product.builder()
                .name(request.name())
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
                .and(ProductSpecifications.nameContains(name))
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

        product.setName(request.name());
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
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getUnit(),
                product.getExpirationDate(),
                product.getLocation(),
                calculateStatus(product),
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
            case "name" -> "name";
            case "quantity" -> "quantity";
            case "expirationDate" -> "expirationDate";
            case "location" -> "location";
            default -> "createdAt";
        };

        Sort.Direction sortDirection =
                "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        return Sort.by(sortDirection, sortField);
    }
}