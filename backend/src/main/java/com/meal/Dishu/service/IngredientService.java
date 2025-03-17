package com.meal.Dishu.service;

import org.springframework.stereotype.Service;

import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {
    
    private final IngredientRepository ingredientRepository;

    public Ingredient creaIngredient(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }
}
