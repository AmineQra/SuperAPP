package com.meal.Dishu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meal.Dishu.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByName(String name);

    @Query(value = "SELECT * FROM ingredient WHERE LOWER(name) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Ingredient> fuzzySearchIngredients(@Param("search") String search);


}
