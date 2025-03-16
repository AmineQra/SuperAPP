package com.meal.Dishu.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.service.RecipeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;


public class RecipeControllerTest {

    private MockMvc mockMvc;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private RecipeService recipeService;

    @InjectMocks 
    private RecipeController recipeController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void shouldReturnAllRecipesSuccessfully() throws Exception {
        List<Recipe> recipes = List.of(
            new Recipe(1L, "Pizza", "Delicious cheese pizza", null),
            new Recipe(2L, "Burger", "Delicious cheese Burger", null)
        );
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(get("/api/recipes"))
            .andExpect(jsonPath("$.size()")
            .value(2))
            .andExpect(jsonPath("$[0].name").value("Pizza"));
        
    }

    @Test
    void shouldReturnRecipeByIdSuccessfully() throws Exception {
        Recipe recipe = new Recipe(1L, "Pizza", "Cheese pizza", null);
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/api/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    void shouldCreateRecipeSuccessfully() throws Exception {
        Recipe recipe = new Recipe(null, "Pizza", "Delicious cheese pizza", null);
        when(recipeService.createRecipe(any(Recipe.class))).thenReturn(recipe);
        
        mockMvc.perform(post("/api/recipes/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"));
    }

    @Test
    void shouldDeleteRecipe() throws Exception {
        doNothing().when(recipeService).deleteRecipe(1L);

        mockMvc.perform(delete("/api/recipes/delete/1"))
                .andExpect(status().isNoContent());
    }


}
