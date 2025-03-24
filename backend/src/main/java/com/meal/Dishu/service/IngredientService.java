package com.meal.Dishu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.meal.Dishu.dto.IngredientRequestDto;
import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.IngredientDocument;
import com.meal.Dishu.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientSearchService ingredientSearchService;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    public Ingredient createIngredient(IngredientRequestDto ingredientRequestDto) {
        System.out.println("ingredientRequestDto = " + ingredientRequestDto);

        if (ingredientRepository.findByName(ingredientRequestDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ingredient with name " + ingredientRequestDto.getName() + " already exists");

        }
        Ingredient ingredient = Ingredient.builder()
                .name(ingredientRequestDto.getName())
                .quantity(ingredientRequestDto.getQuantity())
                .proteins(ingredientRequestDto.getProtein())
                .carbs(ingredientRequestDto.getCarbs())
                .fats(ingredientRequestDto.getFat())
                .build();
        
        
        
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        ingredientSearchService.saveIngredient(savedIngredient);

        return savedIngredient;
    }

    public void deleteIngredient(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with ID " + id + " not found");
        }
        ingredientSearchService.deleteById(id.toString());
        ingredientRepository.deleteById(id);
    }

    public List<Ingredient> findAllById(List<IngredientDocument> ingredientDocuments){
        List<Long> ingredientsIdList = ingredientDocuments.stream().map(ingredient -> Long.parseLong(ingredient.getId())).toList();
        return ingredientRepository.findAllById(ingredientsIdList);
    }

}
