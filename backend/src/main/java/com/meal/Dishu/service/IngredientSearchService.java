package com.meal.Dishu.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.meal.Dishu.model.Ingredient;
import com.meal.Dishu.model.IngredientDocument;
import com.meal.Dishu.repository.IngredientSeachRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private final IngredientSeachRepository ingredientSeachRepository;

    public List<IngredientDocument> searchIngredients(String searchTerm) throws IOException{
        Query query = MatchQuery.of(m -> m
            .field("name")
            .query(searchTerm)
            .fuzziness("AUTO")
        )._toQuery();

        SearchResponse<IngredientDocument> response = elasticsearchClient.search(s -> s
            .index("ingredients")
            .query(query),
            IngredientDocument.class
        );

        return response.hits().hits().stream()
            .map(hit -> hit.source())
            .collect(Collectors.toList());
    }

     public void saveIngredient(Ingredient ingredient) {
        IngredientDocument ingredientDocument = new IngredientDocument();
        String ingredientIdString = ingredient.getId().toString();

        ingredientDocument.setId(ingredientIdString);
        ingredientDocument.setName(ingredient.getName());
        ingredientSeachRepository.save(ingredientDocument);
    }

    public void deleteById(String ingredientId) {
        ingredientSeachRepository.deleteById(ingredientId);
    }
}
