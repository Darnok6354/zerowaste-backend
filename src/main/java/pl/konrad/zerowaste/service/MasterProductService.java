package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.MasterProduct;
import pl.konrad.zerowaste.dto.MasterProductResponse;
import pl.konrad.zerowaste.repository.MasterProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasterProductService {

    private final MasterProductRepository masterProductRepository;

    public List<MasterProductResponse> getMasterProducts(String query) {
        List<MasterProduct> products;

        if (query != null && !query.isBlank()) {
            products = masterProductRepository.findByNameContainingIgnoreCase(query);
        } else {
            products = masterProductRepository.findAll();
        }

        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private MasterProductResponse mapToResponse(MasterProduct product) {
        return new MasterProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getImageUrl(),
                product.getBarcode(),
                product.getDefaultUnit()
        );
    }
}