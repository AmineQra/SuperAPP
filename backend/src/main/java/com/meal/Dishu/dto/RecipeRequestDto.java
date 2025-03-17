package com.meal.Dishu.dto;

import java.util.Set;

import com.meal.Dishu.enumeration.RecipeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequestDto {

    @NotBlank(message = "name is required")
    @Size(min = 3, max = 30, message = "name between 3 and 30 character")
    private String name;

    private String description;

    @NotBlank(message = "Minimum 1 ingredient")
    private Set<Long> ingredients;

    @NotBlank(message = "minimum 1 type")
    private Set<RecipeType> types;
}
