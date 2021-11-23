package com.home.recipe.repository;

import com.home.recipe.model.Ingredient;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

  List<Ingredient> findByRecipeId(Long recipeId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Ingredient b WHERE b.recipe.id = ?1")
  void deleteByRecipeId(Long recipeId);
}
