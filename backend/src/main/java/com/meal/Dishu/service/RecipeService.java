package com.meal.Dishu.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Recipe createRecipe(RecipeRequestDto recipeRequestDto){
        Recipe recipe = new Recipe();
        recipe.setName(recipeRequestDto.getName());
        recipe.setDescription(recipeRequestDto.getDescription());

        Set<Ingredient> ingredients = ingredientRepository.findAllById(recipeRequestDto.getIngredients())
                .stream()
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);

        recipe.setTypes(recipeRequestDto.getTypes());

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
