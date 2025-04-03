package com.meal.Dishu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.IngredientDocument;
import com.meal.Dishu.repository.IngredientRepository;
import com.meal.Dishu.repository.IngredientSeachRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientSyncService {

    private final IngredientRepository ingredientRepository;
    private final IngredientSeachRepository ingredientSeachRepository;

    @PostConstruct
    public void syncIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        List<IngredientDocument> ingredientDocuments = ingredients.stream()
                .map(ingredient -> new IngredientDocument(ingredient.getId().toString(), ingredient.getName()))
                .toList();

        ingredientSeachRepository.saveAll(ingredientDocuments);

    }
}
