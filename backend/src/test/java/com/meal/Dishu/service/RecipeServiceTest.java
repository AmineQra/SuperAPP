package com.meal.Dishu.service;

import com.meal.Dishu.dto.RecipeRequestDto;
import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.repository.IngredientRepository;
import com.meal.Dishu.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateRecipeSuccessfully() {
        Set<Long> dtoIngredients = Set.of(1L);
        Ingredient ingredient = new Ingredient(1L, "pomme", 2.2, 2.2, 2.2, 2.2);
        Set<Ingredient> ingredients = Set.of(ingredient);
        RecipeRequestDto recipeRequestDto = new RecipeRequestDto("Pizza", "Delicious cheese pizza", dtoIngredients, null);
        Recipe recipe = new Recipe(1L, "Pizza", "Delicious cheese pizza", ingredients, null);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Recipe createdRecipe = recipeService.createRecipe(recipeRequestDto);

        assertNotNull(createdRecipe);
        assertEquals("Pizza", createdRecipe.getName());
    }

    @Test
    void shouldReturnAllRecipes() {
        List<Recipe> recipes = Arrays.asList(
                new Recipe(1L, "Pizza", "Delicious cheese pizza", null, null),
                new Recipe(2L, "Pasta", "Tasty pasta with tomato sauce", null, null)
        );
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(2, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }

    @Test
    void shouldReturnRecipeById() {
        Recipe recipe = new Recipe(1L, "Pizza", "Delicious cheese pizza", null, null);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> foundRecipe = recipeService.getRecipeById(1L);

        assertTrue(foundRecipe.isPresent());
        assertEquals("Pizza", foundRecipe.get().getName());
    }

    @Test
    void shouldDeleteRecipeSuccessfully() {
        doNothing().when(recipeRepository).deleteById(1L);

        recipeService.deleteRecipe(1L);

        verify(recipeRepository, times(1)).deleteById(1L);
    }
}
