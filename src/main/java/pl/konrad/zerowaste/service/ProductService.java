package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductStatus;
import pl.konrad.zerowaste.domain.ProductUnit;
import pl.konrad.zerowaste.domain.StorageLocation;
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

    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .quantity(request.quantity())
                .unit(request.unit())
                .expirationDate(request.expirationDate())
                .location(request.location())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
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
        Specification<Product> specification = Specification
                .where(ProductSpecifications.nameContains(name))
                .and(ProductSpecifications.hasLocation(location))
                .and(ProductSpecifications.hasUnit(unit))
                .and(ProductSpecifications.hasStatus(status));

        Sort sort = buildSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(specification, pageable)
                .map(this::mapToResponse);
    }
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(request.name());
        product.setQuantity(request.quantity());
        product.setUnit(request.unit());
        product.setExpirationDate(request.expirationDate());
        product.setLocation(request.location());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
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
        String sortField = mapSortField(sortBy);
        Sort.Direction sortDirection =
                "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        return Sort.by(sortDirection, sortField);
    }

    private String mapSortField(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "createdAt";
        }

        return switch (sortBy) {
            case "name" -> "name";
            case "quantity" -> "quantity";
            case "expirationDate" -> "expirationDate";
            case "createdAt" -> "createdAt";
            case "location" -> "location";
            default -> "createdAt";
        };
    }
}