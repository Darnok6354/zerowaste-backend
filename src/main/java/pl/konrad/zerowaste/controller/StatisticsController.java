package pl.konrad.zerowaste.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.dto.MonthlyStatisticsResponse;
import pl.konrad.zerowaste.service.StatisticsService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyStatisticsResponse> getMonthlyStats(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        // Jeśli frontend nie poda daty, bierzemy obecny miesiąc i rok
        LocalDate today = LocalDate.now();
        int targetYear = (year != null) ? year : today.getYear();
        int targetMonth = (month != null) ? month : today.getMonthValue();

        return ResponseEntity.ok(statisticsService.getMonthlyStatistics(targetYear, targetMonth));
    }
}