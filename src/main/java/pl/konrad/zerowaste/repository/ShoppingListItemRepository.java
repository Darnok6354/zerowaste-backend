package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konrad.zerowaste.domain.ShoppingListItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
    List<ShoppingListItem> findAllByUserProfile_IdOrderByCreatedAtDesc(Long userId);
    Optional<ShoppingListItem> findByIdAndUserProfile_Id(Long id, Long userId);
}