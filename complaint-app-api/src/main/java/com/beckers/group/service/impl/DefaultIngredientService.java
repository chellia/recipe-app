package com.beckers.group.service.impl;

import com.beckers.group.exception.ResourceNotFoundException;
import com.beckers.group.model.Ingredient;
import com.beckers.group.model.Recipe;
import com.beckers.group.repository.IngredientRepository;
import com.beckers.group.repository.RecipeRepository;
import com.beckers.group.service.IngredientService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class DefaultIngredientService implements IngredientService {

  private final RecipeRepository recipeRepository;
  private final IngredientRepository ingredientRepository;

  @Autowired
  DefaultIngredientService(
    RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
    this.recipeRepository = recipeRepository;
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public Ingredient createIngredient(Ingredient ingredient) {
    Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredient.getRecipe().getId());
    if (!optionalRecipe.isPresent()) {
      log.debug(String.format("Could not find recipe with ID(%s) provided",ingredient.getRecipe().getId()));
      throw new ResourceNotFoundException(String.format("Could not find recipe with ID(%s) provided",ingredient.getRecipe().getId()));
    }
    ingredient.setRecipe(optionalRecipe.get());
    return ingredientRepository.save(ingredient);
  }

  @Override
  public Ingredient getIngredientById(Long id) {
    return ingredientRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Could not find ingredient with ID "
        + "provided"));
  }

  @Override
  public List<Ingredient> getByRecipeId(Long id) {
    return ingredientRepository.findByRecipeId(id);
  }

  @Override
  public void deleteIngredientById(Long ingredientId) {
    Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
    if (ingredient.isPresent()) {
      ingredientRepository.delete(ingredient.get());
    } else {
      throw new ResourceNotFoundException("Could not find ingredient with ID provided");
    }
  }

  @Override
  public Ingredient updateIngredient(Long incredientId, Ingredient ingredient) {
    Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredient.getRecipe().getId());
    if (!optionalRecipe.isPresent()) {
      throw new ResourceNotFoundException("Could not find recipe with ID provided");
    }
    Optional<Ingredient> optionalIngredient = ingredientRepository.findById(incredientId);
    if (!optionalIngredient.isPresent()) {
      throw new ResourceNotFoundException("Could not find ingredient with ID provided");
    }
    ingredient.setRecipe(optionalRecipe.get());
    ingredient.setId(incredientId);
    return ingredientRepository.save(ingredient);
  }
}
