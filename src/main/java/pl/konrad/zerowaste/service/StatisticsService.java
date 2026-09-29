package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.dto.MonthlyStatisticsResponse;
import pl.konrad.zerowaste.dto.WastedProductStat;
import pl.konrad.zerowaste.repository.ProductHistoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ProductHistoryRepository productHistoryRepository;
    private final CurrentUserService currentUserService;

    public MonthlyStatisticsResponse getMonthlyStatistics(int year, int month) {
        UserProfile currentUser = currentUserService.getCurrentUser();

        // Wyznaczamy początek i koniec zadanego miesiąca
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

        // 1. Liczymy zjedzone
        long totalConsumed = productHistoryRepository.countUserActionsInPeriod(
                currentUser.getId(), ProductLifecycleStatus.CONSUMED, startDate, endDate
        );

        // 2. Liczymy wyrzucone
        long totalWasted = productHistoryRepository.countUserActionsInPeriod(
                currentUser.getId(), ProductLifecycleStatus.WASTED, startDate, endDate
        );

        // 3. Pobieramy TOP 5 wyrzucanych produktów
        List<WastedProductStat> topWasted = productHistoryRepository.findTopWastedProductsInPeriod(
                currentUser.getId(), startDate, endDate, PageRequest.of(0, 5)
        );

        return new MonthlyStatisticsResponse(
                year,
                month,
                totalConsumed,
                totalWasted,
                topWasted
        );
    }
}