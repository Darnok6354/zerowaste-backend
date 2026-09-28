package pl.konrad.zerowaste.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.konrad.zerowaste.domain.Product;

import java.time.LocalDate;
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
}