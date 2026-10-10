package pl.konrad.zerowaste.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.konrad.zerowaste.repository.ProductRepository;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductLifecycleScheduler {

    private final ProductRepository productRepository;

    // Cron "0 0 0 * * *" oznacza uruchomienie codziennie o północy (00:00:00)
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateProductStatusesAutomatically() {
        log.info("Rozpoczynam automatyczną aktualizację statusów produktów...");
        LocalDate today = LocalDate.now();

        // Jeśli używasz statusu EXPIRING_SOON (np. ostrzeżenie na 2 dni przed zepsuciem)
        LocalDate warningDate = today.plusDays(3);
        int soonCount = productRepository.updateExpiringSoonProducts(today, warningDate);

        // Aktualizacja produktów przeterminowanych
        int expiredCount = productRepository.updateExpiredProducts(today);

        log.info("Zakończono aktualizację. Oznaczono jako przeterminowane: {}, Oznaczono jako krótki termin: {}", expiredCount, soonCount);
    }
}