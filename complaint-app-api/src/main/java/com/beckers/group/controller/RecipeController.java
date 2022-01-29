package com.beckers.group.controller;

import com.beckers.group.model.Recipe;
import com.beckers.group.request.RecipeDto;
import com.beckers.group.request.RecipeUpdateDto;
import com.beckers.group.service.RecipeService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

  private final RecipeService recipeService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  public RecipeController(RecipeService recipeService) {
    this.recipeService = recipeService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<RecipeDto> getAllRecipes() {
    List<Recipe> recipes = recipeService.getAllRecipes();
    return recipes.stream().map(this::convertToRecipeDto).collect(Collectors.toList());
  }

  @GetMapping(value = "{recipeId}")
  @ResponseStatus(HttpStatus.OK)
  public RecipeDto getRecipe(
    @PathVariable
      Long recipeId) {
    Recipe recipe = recipeService.getRecipeById(recipeId);
    return convertToRecipeDto(recipe);
  }

  @DeleteMapping("{recipeId}")
  void deleteRecipeById(
    @PathVariable
      Long recipeId) {
    recipeService.deleteRecipeById(recipeId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RecipeDto createRecipe(
    @RequestBody
      RecipeDto recipeDto) {
    Recipe recipe = recipeService.createRecipe(convertToRecipeEntity(recipeDto));
    return convertToRecipeDto(recipe);
  }

  @PutMapping("{recipeId}")
  @ResponseStatus(HttpStatus.OK)
  public RecipeDto updateRecipe(
    @PathVariable
      Long recipeId,
    @RequestBody
      RecipeUpdateDto recipeUpdateDto) {
    Recipe recipe = recipeService.getRecipeById(recipeId);
    updateRecipeFromDto(recipe, recipeUpdateDto);
    recipeService.updateRecipe(recipe);
    return convertToRecipeDto(recipe);
  }

  private void updateRecipeFromDto(Recipe recipe, RecipeUpdateDto recipeUpdateDto) {
    recipe.setName(recipeUpdateDto.getName());
    recipe.setVegetarian(recipeUpdateDto.isVegetarian());
    recipe.setServings(recipeUpdateDto.getServings());
    recipe.setInstructions(recipeUpdateDto.getInstructions());
    /*recipe.getIngredients().clear();
    recipe
        .getIngredients()
        .addAll(recipeUpdateDto
            .getIngredients()
            .stream()
            .map(this::convertToIngredientEntity)
            .collect(Collectors.toList()));*/
  }


  private Recipe convertToRecipeEntity(RecipeDto recipeDto) {
    Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
   /* recipe.setIngredients(recipeDto
        .getIngredients()
        .stream()
        .map(this::convertToIngredientEntity)
        .collect(Collectors.toList()));*/
    recipe.setCreatedDateTime(recipeDto.stringFormatToLocalDateTime(recipeDto.getCreatedDateTime()));
    return recipe;
  }

  private RecipeDto convertToRecipeDto(Recipe recipe) {
    RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
   /* recipeDto.setIngredients(recipe
        .getIngredients()
        .stream()
        .map(this::convertToIngredientDto)
        .collect(Collectors.toList()));*/
    recipeDto.setCreatedDateTime(recipeDto.localDateTimeToStringFormat(recipe.getCreatedDateTime()));
    return recipeDto;
  }

}
