package com.meal.Dishu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meal.Dishu.dto.IngredientRequestDto;
import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.IngredientDocument;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.model.RecipeDocument;
import com.meal.Dishu.service.IngredientSearchService;
import com.meal.Dishu.service.IngredientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientSearchService ingredientSearchService;

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients(@RequestParam(required = false) String search)
            throws IOException {
        List<IngredientDocument> ingredientsDocument;
        List<Ingredient> ingredients;

        if (search != null && !search.isEmpty()) {
            ingredientsDocument = ingredientSearchService.searchIngredients(search);
            ingredients = ingredientService.findAllById(ingredientsDocument);
        } else {
            ingredients = ingredientService.getAllIngredients();
        }
        return ResponseEntity.ok(ingredients);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Ingredient>> searchIngredient(@RequestBody String query) {
        try {
            List<IngredientDocument> searchResult = ingredientSearchService.searchIngredients(query);
            List<Ingredient> ingredients = ingredientService.findAllById(searchResult);
            return ResponseEntity.ok(ingredients);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details for debugging
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") Long id) {
        Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
        return ingredient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createIngredient(@Valid @RequestBody IngredientRequestDto ingredientRequestDto,
            BindingResult bindingResult) {
        ingredientService.createIngredient(ingredientRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

}
