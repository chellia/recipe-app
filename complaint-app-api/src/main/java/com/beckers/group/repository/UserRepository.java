package com.beckers.group.repository;

import com.beckers.group.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  /*List<Ingredient> findByRecipeId(Long recipeId);

  @Modifying
  @Transactional
  @Query("DELETE FROM Ingredient b WHERE b.recipe.id = ?1")
  void deleteByRecipeId(Long recipeId);*/
}
