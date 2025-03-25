package com.meal.Dishu.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import com.meal.Dishu.model.Recipe;
import com.meal.Dishu.model.RecipeDocument;
import com.meal.Dishu.repository.RecipeSearchRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private final RecipeSearchRepository recipeSearchRepository;

    public List<RecipeDocument> searchRecipes(String searchTerm) throws IOException {
        Query query = MatchQuery.of(m -> m
            .field("name")
            .query(searchTerm)
            .fuzziness("AUTO")
        )._toQuery();

        // Execute the search
        SearchResponse<RecipeDocument> response = elasticsearchClient.search(s -> s
            .index("recipes")
            .query(query),
            RecipeDocument.class
        );

        // Process results
        return response.hits().hits().stream()
            .map(hit -> hit.source())
            .collect(Collectors.toList());
    }

    public void saveRecipe(Recipe recipe) {
        RecipeDocument recipeDocument = new RecipeDocument();

        recipeDocument.setId(recipe.getId().toString());
        recipeDocument.setName(recipe.getName());
        recipeSearchRepository.save(recipeDocument);
    }

    public void deleteById(String recipeId) {
        recipeSearchRepository.deleteById(recipeId);
    }

}