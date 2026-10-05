package pl.konrad.zerowaste.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.dto.SavedRecipeRequest;
import pl.konrad.zerowaste.dto.SavedRecipeResponse;
import pl.konrad.zerowaste.service.SavedRecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/saved-recipes")
@RequiredArgsConstructor
public class SavedRecipeController {

    private final SavedRecipeService service;

    @PostMapping
    public ResponseEntity<SavedRecipeResponse> saveRecipe(@RequestBody SavedRecipeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveRecipe(request));
    }

    @GetMapping
    public ResponseEntity<List<SavedRecipeResponse>> getMySavedRecipes() {
        return ResponseEntity.ok(service.getMySavedRecipes());
    }

    @DeleteMapping("/{spoonacularId}")
    public ResponseEntity<Void> removeRecipe(@PathVariable Long spoonacularId) {
        service.removeRecipe(spoonacularId);
        return ResponseEntity.noContent().build();
    }
}