package com.home.recipe.service.impl;

import com.home.recipe.exception.BadRequestException;
import com.home.recipe.exception.ResourceAlreadyExistsException;
import com.home.recipe.exception.ResourceNotFoundException;
import com.home.recipe.model.Recipe;
import com.home.recipe.repository.IngredientRepository;
import com.home.recipe.repository.RecipeRepository;
import com.home.recipe.service.RecipeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class DefaultRecipeService implements RecipeService {

  private final RecipeRepository recipeRepository;
  private final IngredientRepository ingredientRepository;

  @Autowired
  DefaultRecipeService(
    RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
    this.recipeRepository = recipeRepository;
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public Recipe getRecipeById(Long id) {
    return recipeRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Could not find recipe with ID provided"));
  }

  @Override
  public void deleteRecipeById(Long recipeId) {
    Optional<Recipe> recipe = recipeRepository.findById(recipeId);
    if (recipe.isPresent()) {
      deleteRecipeInTransaction(recipe.get());
    } else {
      throw new ResourceNotFoundException("Could not find recipe with ID provided");
    }
  }

  @Transactional
  public void deleteRecipeInTransaction(Recipe recipe) {
    ingredientRepository.deleteByRecipeId(recipe.getId());
    recipeRepository.delete(recipe);
  }

  @Override
  public List<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
  }

  @Override
  public Recipe createRecipe(Recipe recipe) {
    if (recipe.getId() != null && recipeRepository.existsById(recipe.getId())) {
      throw new ResourceAlreadyExistsException("The ID is already exists");
    }
    return recipeRepository.save(recipe);
  }

  @Override
  public Recipe updateRecipe(Recipe recipe) {
    if (recipe.getId() != null) {
      return recipeRepository.save(recipe);
    }
    throw new BadRequestException("The ID must be provided when updating a Recipe");
  }
}
