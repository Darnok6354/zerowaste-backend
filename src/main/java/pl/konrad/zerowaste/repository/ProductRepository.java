package pl.konrad.zerowaste.repository;

import pl.konrad.zerowaste.domain.ProductLifecycleStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.konrad.zerowaste.domain.Product;
import pl.konrad.zerowaste.domain.ProductStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndUserProfile_Id(Long id, Long userProfileId);

    boolean existsByIdAndUserProfile_Id(Long id, Long userProfileId);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    long countByUserProfile_Id(Long userProfileId);

    long countByUserProfile_IdAndExpirationDateBefore(Long userProfileId, LocalDate date);

    long countByUserProfile_IdAndExpirationDateBetween(Long userProfileId, LocalDate startDate, LocalDate endDate);

    long countByUserProfile_IdAndExpirationDateAfter(Long userProfileId, LocalDate date);

    long countByUserProfile_IdAndExpirationDateIsNull(Long userProfileId);

    // Pobiera wszystkie produkty użytkownika o określonym statusie cyklu życia
    List<Product> findAllByUserProfile_IdAndLifecycleStatus(Long userId, ProductLifecycleStatus lifecycleStatus);

    // ZASTĄPIONE: Pobiera produkty psujące się (data ważności między dzisiaj a podanym progiem)
    List<Product> findByUserProfile_IdAndLifecycleStatusAndExpirationDateBetween(
            Long userId,
            ProductLifecycleStatus lifecycleStatus,
            LocalDate startDate,
            LocalDate endDate
    );

    // NOWE: Pobiera produkty świeże (data ważności powyżej progu LUB brak daty ważności)
    @Query("SELECT p FROM Product p WHERE p.userProfile.id = :userId " +
            "AND p.lifecycleStatus = :lifecycleStatus " +
            "AND (p.expirationDate > :thresholdDate OR p.expirationDate IS NULL)")
    List<Product> findFreshActiveProducts(
            @Param("userId") Long userId,
            @Param("lifecycleStatus") ProductLifecycleStatus lifecycleStatus,
            @Param("thresholdDate") LocalDate thresholdDate
    );

    // Opcjonalnie: Jeśli całkowicie usuwasz kolumnę "status" z bazy, możesz usunąć poniższe metody @Modifying.
    // Zostawiam je zakomentowane jako referencję, w razie gdyby były wpięte w inny system cykliczny.
    /*
    @Modifying
    @Query("UPDATE Product p SET p.status = 'EXPIRED' WHERE p.status != 'EXPIRED' AND p.expirationDate < :today")
    int updateExpiredProducts(@Param("today") LocalDate today);

    @Modifying
    @Query("UPDATE Product p SET p.status = 'EXPIRING_SOON' WHERE p.status = 'FRESH' AND p.expirationDate BETWEEN :today AND :warningDate")
    int updateExpiringSoonProducts(@Param("today") LocalDate today, @Param("warningDate") LocalDate warningDate);
    */
}