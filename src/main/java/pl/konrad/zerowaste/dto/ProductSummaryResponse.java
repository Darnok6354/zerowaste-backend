package pl.konrad.zerowaste.dto;

public record ProductSummaryResponse(
        long total,
        long fresh,
        long expiringSoon,
        long expired
) {
}