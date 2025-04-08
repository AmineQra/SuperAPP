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
import com.meal.Dishu.dto.RecipeRequestDto;
import com.meal.Dishu.enumeration.RecipeType;
import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.service.RecipeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RecipeControllerTest {

        private MockMvc mockMvc;

        private ObjectMapper objectMapper = new ObjectMapper();

        @Mock
        private RecipeService recipeService;

        @InjectMocks
        private RecipeController recipeController;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        }

        @Test
        void shouldReturnAllRecipesSuccessfully() throws Exception {
                List<Recipe> recipes = List.of(
                                new Recipe(1L, "Pizza", "Delicious cheese pizza", null, null, null),
                                new Recipe(2L, "Burger", "Delicious cheese Burger", null, null, null));
                when(recipeService.getAllRecipes()).thenReturn(recipes);

                mockMvc.perform(get("/recipes"))
                                .andExpect(jsonPath("$.size()")
                                                .value(2))
                                .andExpect(jsonPath("$[0].name").value("Pizza"));

        }

        @Test
        void shouldReturnRecipeByIdSuccessfully() throws Exception {
                Recipe recipe = new Recipe(1L, "Pizza", "Cheese pizza", null, null, null);
                when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(recipe));

                mockMvc.perform(get("/recipes/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Pizza"));
        }

        @Test
        void shouldCreateRecipeSuccessfully() throws Exception {
                RecipeType recipeType = RecipeType.MATIN;
                RecipeRequestDto recipeRequestDto = new RecipeRequestDto(null, "Pizza", "Delicious cheese pizza",
                                Set.of(1L),
                                Set.of(recipeType), null);
                Recipe recipe = new Recipe(null, "Pizza", "Delicious cheese pizza", null, null, null);
                when(recipeService.createRecipe(any(RecipeRequestDto.class))).thenReturn(recipe);

                mockMvc.perform(post("/recipes/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipeRequestDto)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Pizza"));
        }

        @Test
        void shouldDeleteRecipe() throws Exception {
                doNothing().when(recipeService).deleteRecipe(1L);

                mockMvc.perform(delete("/recipes/delete/1"))
                                .andExpect(status().isNoContent());
        }

}
