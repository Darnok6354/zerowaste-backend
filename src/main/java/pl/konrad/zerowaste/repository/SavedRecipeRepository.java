package pl.konrad.zerowaste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.konrad.zerowaste.domain.SavedRecipe;
import java.util.List;
import java.util.Optional;

public interface SavedRecipeRepository extends JpaRepository<SavedRecipe, Long> {

    // Pobiera wszystkie zapisane przepisy danego użytkownika, sortując od najnowszych
    List<SavedRecipe> findAllByUserProfile_IdOrderBySavedAtDesc(Long userId);

    // Szuka konkretnego zapisanego przepisu, żeby można było go usunąć z ulubionych
    Optional<SavedRecipe> findByUserProfile_IdAndSpoonacularRecipeId(Long userId, Long spoonacularRecipeId);

    // Szybkie sprawdzenie, czy użytkownik ma już ten przepis w ulubionych
    boolean existsByUserProfile_IdAndSpoonacularRecipeId(Long userId, Long spoonacularRecipeId);
}