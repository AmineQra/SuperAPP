package com.meal.Dishu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meal.Dishu.dto.RecipeRequestDto;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.model.RecipeDocument;
import com.meal.Dishu.service.RecipeSearchService;
import com.meal.Dishu.service.RecipeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService recipeService;
    private final RecipeSearchService recipeSearchService;

    @GetMapping
     public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
     public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        Recipe newRecipe = recipeService.createRecipe(recipeRequestDto);
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestBody String query) {
        try {
            List<RecipeDocument> searchResult = recipeSearchService.searchRecipes(query);
            List<Recipe> recipes = recipeService.findAllById(searchResult);
            return ResponseEntity.ok(recipes);
        }
        catch(IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        catch(Exception e) {
            e.printStackTrace();  // Log the exception details for debugging
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
    
}
