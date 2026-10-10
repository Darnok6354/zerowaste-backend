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

    // Pobiera produkty użytkownika po statusie cyklu życia (np. ACTIVE) oraz statusie przydatności (np. EXPIRING_SOON)
    List<Product> findAllByUserProfile_IdAndLifecycleStatusAndStatus(
            Long userId,
            ProductLifecycleStatus lifecycleStatus,
            ProductStatus status
    );

    @Modifying
    @Query("UPDATE Product p SET p.status = 'EXPIRED' WHERE p.status != 'EXPIRED' AND p.expirationDate < :today")
    int updateExpiredProducts(@Param("today") LocalDate today);

    // 2. (Opcjonalnie) Zmiana na EXPIRING_SOON dla produktów, którym zostały np. 2 dni
    @Modifying
    @Query("UPDATE Product p SET p.status = 'EXPIRING_SOON' WHERE p.status = 'FRESH' AND p.expirationDate BETWEEN :today AND :warningDate")
    int updateExpiringSoonProducts(@Param("today") LocalDate today, @Param("warningDate") LocalDate warningDate);
}