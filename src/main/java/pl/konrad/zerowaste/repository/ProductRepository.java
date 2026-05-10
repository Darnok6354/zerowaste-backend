package pl.konrad.zerowaste.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.konrad.zerowaste.domain.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndUserProfile_Id(Long id, Long userProfileId);

    boolean existsByIdAndUserProfile_Id(Long id, Long userProfileId);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}