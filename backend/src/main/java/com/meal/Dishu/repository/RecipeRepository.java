package com.meal.Dishu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meal.Dishu.model.Recipe;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
