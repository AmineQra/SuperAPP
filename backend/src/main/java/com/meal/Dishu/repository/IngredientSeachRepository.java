package com.meal.Dishu.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.meal.Dishu.model.IngredientDocument;

@Repository
public interface IngredientSeachRepository extends ElasticsearchRepository<IngredientDocument, String> {
    
}
