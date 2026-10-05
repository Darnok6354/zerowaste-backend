package pl.konrad.zerowaste.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.konrad.zerowaste.dto.SavedRecipeRequest;
import pl.konrad.zerowaste.dto.SavedRecipeResponse;
import pl.konrad.zerowaste.domain.SavedRecipe;
import pl.konrad.zerowaste.domain.UserProfile;
import pl.konrad.zerowaste.exception.ResourceNotFoundException;
import pl.konrad.zerowaste.repository.SavedRecipeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedRecipeService {

    private final SavedRecipeRepository repository;
    private final CurrentUserService currentUserService;

    public SavedRecipeResponse saveRecipe(SavedRecipeRequest request) {
        UserProfile user = currentUserService.getCurrentUser();

        // Zabezpieczenie przed zduplikowaniem tego samego przepisu
        if (repository.existsByUserProfile_IdAndSpoonacularRecipeId(user.getId(), request.spoonacularRecipeId())) {
            throw new IllegalArgumentException("Ten przepis znajduje się już w Twoich ulubionych.");
        }

        SavedRecipe savedRecipe = SavedRecipe.builder()
                .spoonacularRecipeId(request.spoonacularRecipeId())
                .title(request.title())
                .imageUrl(request.imageUrl())
                .userProfile(user)
                .build();

        return mapToResponse(repository.save(savedRecipe));
    }

    public List<SavedRecipeResponse> getMySavedRecipes() {
        UserProfile user = currentUserService.getCurrentUser();
        return repository.findAllByUserProfile_IdOrderBySavedAtDesc(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void removeRecipe(Long spoonacularRecipeId) {
        UserProfile user = currentUserService.getCurrentUser();
        SavedRecipe recipe = repository.findByUserProfile_IdAndSpoonacularRecipeId(user.getId(), spoonacularRecipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono przepisu o ID: " + spoonacularRecipeId + " w ulubionych."));

        repository.delete(recipe);
    }

    private SavedRecipeResponse mapToResponse(SavedRecipe recipe) {
        return new SavedRecipeResponse(
                recipe.getId(),
                recipe.getSpoonacularRecipeId(),
                recipe.getTitle(),
                recipe.getImageUrl(),
                recipe.getSavedAt()
        );
    }
}