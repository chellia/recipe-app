package com.home.recipe.service;

import com.home.recipe.model.Recipe;
import java.util.List;

public interface RecipeService {

  List<Recipe> getAllRecipes();

  Recipe getRecipeById(Long id);

  void deleteRecipeById(Long id);

  Recipe createRecipe(Recipe recipe);

  Recipe updateRecipe(Recipe recipe);

}
