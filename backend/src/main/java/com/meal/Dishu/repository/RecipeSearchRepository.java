package com.meal.Dishu.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.meal.Dishu.model.RecipeDocument;

@Repository
public interface RecipeSearchRepository extends ElasticsearchRepository<RecipeDocument, String>{

}
