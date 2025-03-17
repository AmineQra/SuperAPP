package com.meal.Dishu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDto {
  @NotBlank(message = "name is required")
    @Size(min = 3, max = 30, message = "name between 3 and 30 character")
    private String name;

    private double quantity;

    private double proteine;

    private double carbs;

    private double fat;

}
