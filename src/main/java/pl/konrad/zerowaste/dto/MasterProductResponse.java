package pl.konrad.zerowaste.dto;

public record MasterProductResponse(
        Long id,
        String name,
        String category,
        String imageUrl,
        String barcode
) {
}