package com.meal.Dishu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.meal.Dishu.dto.RecipeRequestDto;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    
    private final RecipeRepository recipeRepository;

    public Recipe createRecipe(RecipeRequestDto recipeRequestDto){
        return recipeRepository.save(recipeRequestDto);
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
