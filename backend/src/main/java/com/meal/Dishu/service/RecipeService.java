package com.meal.Dishu.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.meal.Dishu.dto.RecipeRequestDto;
import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.repository.RecipeRepository;
import com.meal.Dishu.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public Set<Ingredient> retrievIngredients(RecipeRequestDto recipeRequestDto){
        return ingredientRepository.findAllById(recipeRequestDto.getIngredients())
        .stream()
        .collect(Collectors.toSet());
    }

    public Recipe createRecipe(RecipeRequestDto recipeRequestDto){
        Set<Ingredient> ingredients = this.retrievIngredients(recipeRequestDto);

        if(ingredients.isEmpty()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredients request not found");
        }
        Recipe recipe = Recipe.builder()
        .name(recipeRequestDto.getName())
        .description(recipeRequestDto.getDescription())
        .ingredients(ingredients)
        .types(recipeRequestDto.getTypes())
        .build();

        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipeById(Long id){
        return recipeRepository.findById(id);
    }
    
    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public void deleteRecipe(Long id){
        recipeRepository.deleteById(id);
    }
}
