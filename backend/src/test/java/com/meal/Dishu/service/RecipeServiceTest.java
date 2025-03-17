package com.meal.Dishu.service;

import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.repository.RecipeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateRecipeSuccessfully() {
        Recipe recipe = new Recipe(null, "Pizza", "Delicious cheese pizza", null, null);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe createdRecipe = recipeService.createRecipe(recipe);

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
