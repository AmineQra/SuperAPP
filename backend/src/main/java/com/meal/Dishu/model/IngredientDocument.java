package com.meal.Dishu.model;

import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "ingredients")
public class IngredientDocument {
    private String id;

    private String name;
}
