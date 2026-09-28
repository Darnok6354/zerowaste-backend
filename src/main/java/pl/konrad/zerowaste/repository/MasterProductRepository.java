package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konrad.zerowaste.domain.MasterProduct;
import java.util.Optional;

@Repository
public interface MasterProductRepository extends JpaRepository<MasterProduct, Long> {

    // Przydatne na przyszłość, gdy frontend będzie prosił o podpowiedzi przy wpisywaniu nazwy
    Optional<MasterProduct> findByNameIgnoreCase(String name);
}