package com.meal.Dishu.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "recipes")
public class RecipeDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;
}
