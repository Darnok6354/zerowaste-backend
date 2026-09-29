package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konrad.zerowaste.domain.MasterProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterProductRepository extends JpaRepository<MasterProduct, Long> {

    Optional<MasterProduct> findByNameIgnoreCase(String name);

    // Nowa metoda do wyszukiwarki (autocomplete)
    List<MasterProduct> findByNameContainingIgnoreCase(String name);
}