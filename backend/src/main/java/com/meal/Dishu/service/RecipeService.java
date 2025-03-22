package com.meal.Dishu.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.meal.Dishu.dto.RecipeRequestDto;
import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.model.RecipeDocument;
import com.meal.Dishu.repository.RecipeRepository;
import com.meal.Dishu.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeSearchService recipeSearchService;

    public Set<Ingredient> retrievIngredients(RecipeRequestDto recipeRequestDto){
        return ingredientRepository.findAllById(recipeRequestDto.getIngredients())
        .stream()
        .collect(Collectors.toSet());
    }

    public List<Recipe> findAllById(List<RecipeDocument> recipeDocuments){
        List<Long> recipesIdList = recipeDocuments.stream().map(recipe -> Long.parseLong(recipe.getId())).toList();
        return recipeRepository.findAllById(recipesIdList);
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

        Recipe savedRecipe = recipeRepository.save(recipe);

        recipeSearchService.saveRecipe(savedRecipe);

        return savedRecipe;
    }

    public Optional<Recipe> getRecipeById(Long id){
        return recipeRepository.findById(id);
    }
    
    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public void deleteRecipe(Long id){
        recipeSearchService.deleteById(id.toString());
        recipeRepository.deleteById(id);
    }
}
