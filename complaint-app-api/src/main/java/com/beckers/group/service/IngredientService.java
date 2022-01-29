package com.beckers.group.service;

import com.beckers.group.model.Ingredient;
import java.util.List;

public interface IngredientService {

  List<Ingredient> getByRecipeId(Long id);

  Ingredient getIngredientById(Long id);

  void deleteIngredientById(Long id);

  Ingredient createIngredient(Ingredient ingredient);

  Ingredient updateIngredient(Long ingredientId, Ingredient ingredient);

}
