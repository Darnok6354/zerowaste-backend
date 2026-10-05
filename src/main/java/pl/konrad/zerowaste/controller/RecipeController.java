package pl.konrad.zerowaste.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konrad.zerowaste.dto.spoonacular.SpoonacularRecipeDetailsDto;
import pl.konrad.zerowaste.dto.spoonacular.SpoonacularRecipeDto;
import pl.konrad.zerowaste.service.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/suggested")
    public ResponseEntity<List<SpoonacularRecipeDto>> getSuggestedRecipes() {
        return ResponseEntity.ok(recipeService.getRecipesBasedOnInventory());
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<SpoonacularRecipeDetailsDto> getRecipeDetails(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeDetails(id));
    }



}