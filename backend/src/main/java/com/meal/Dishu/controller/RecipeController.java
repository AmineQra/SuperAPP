package com.meal.Dishu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.service.RecipeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService recipeService;

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
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(newRecipe);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("id") Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
    
}
