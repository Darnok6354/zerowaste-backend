package pl.konrad.zerowaste.dto;

import java.util.List;

// Pełny raport dla danego miesiąca
public record MonthlyStatisticsResponse(
        int year,
        int month,
        long totalConsumedItems, // Ile produktów zjedliśmy (wykres kołowy)
        long totalWastedItems,   // Ile produktów wyrzuciliśmy (wykres kołowy)
        List<WastedProductStat> topWastedProducts // Co wyrzucaliśmy (wykres słupkowy)
) {
}