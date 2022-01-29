package com.beckers.group.service;

import com.beckers.group.model.Recipe;
import java.util.List;

public interface RecipeService {

  List<Recipe> getAllRecipes();

  Recipe getRecipeById(Long id);

  void deleteRecipeById(Long id);

  Recipe createRecipe(Recipe recipe);

  Recipe updateRecipe(Recipe recipe);

}
