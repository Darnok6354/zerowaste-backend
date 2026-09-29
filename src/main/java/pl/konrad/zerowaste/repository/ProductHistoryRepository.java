package pl.konrad.zerowaste.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.konrad.zerowaste.domain.ProductHistory;
import pl.konrad.zerowaste.domain.ProductLifecycleStatus;
import pl.konrad.zerowaste.dto.WastedProductStat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {

    // 1. Zliczanie ilości akcji (zjedzone / wyrzucone) w danym okresie
    @Query("SELECT COUNT(h) FROM ProductHistory h WHERE h.userProfile.id = :userId " +
            "AND h.lifecycleStatus = :status " +
            "AND h.timestamp >= :startDate AND h.timestamp < :endDate")
    long countUserActionsInPeriod(
            @Param("userId") Long userId, // lub Long, jeśli Twój userProfile ma ID typu Long
            @Param("status") ProductLifecycleStatus status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // 2. Grupowanie najczęściej wyrzucanych produktów
    @Query("SELECT new pl.konrad.zerowaste.dto.WastedProductStat(h.masterProduct.name, SUM(h.quantity), h.unit) " +
            "FROM ProductHistory h " +
            "WHERE h.userProfile.id = :userId AND h.lifecycleStatus = 'WASTED' " +
            "AND h.timestamp >= :startDate AND h.timestamp < :endDate " +
            "GROUP BY h.masterProduct.name, h.unit " +
            "ORDER BY SUM(h.quantity) DESC")
    List<WastedProductStat> findTopWastedProductsInPeriod(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable // Użyjemy tego, żeby pobrać np. tylko TOP 5
    );
}