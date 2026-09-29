package pl.konrad.zerowaste.dto;
import pl.konrad.zerowaste.domain.ProductUnit;


public record MasterProductResponse(
        Long id,
        String name,
        String category,
        String imageUrl,
        String barcode,
        ProductUnit defaultUnit // <-- NOWOŚĆ
) {
}