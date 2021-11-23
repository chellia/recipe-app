package com.home.recipe.controller;

import com.home.recipe.model.Ingredient;
import com.home.recipe.request.IngredientDto;
import com.home.recipe.service.IngredientService;
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
@RequestMapping("/ingredients")
public class IngredientController {

  private final IngredientService ingredientService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  public IngredientController(IngredientService ingredientService) {
    this.ingredientService = ingredientService;
  }


  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IngredientDto createIngredient(
    @RequestBody
      IngredientDto ingredientDto) {
    Ingredient ingredient = ingredientService.createIngredient(convertToIngredientEntity(
      ingredientDto));
    return convertToIngredientDto(ingredient);
  }

  @PutMapping("{ingredientId}")
  @ResponseStatus(HttpStatus.OK)
  public IngredientDto updateIngredient(
    @RequestBody
      IngredientDto ingredientDto,
    @PathVariable
      Long ingredientId) {
    Ingredient ingredient = ingredientService.updateIngredient(ingredientId,
      convertToIngredientEntity(ingredientDto));
    return convertToIngredientDto(ingredient);
  }

  @DeleteMapping("{ingredientId}")
  void deleteRecipeById(
    @PathVariable
      Long ingredientId) {
    ingredientService.deleteIngredientById(ingredientId);
  }

  @GetMapping(value = "{ingredientId}")
  @ResponseStatus(HttpStatus.OK)
  public IngredientDto getRecipe(
    @PathVariable
      Long ingredientId) {
    Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
    return convertToIngredientDto(ingredient);
  }

  @GetMapping("/recipe/{recipeId}")
  @ResponseStatus(HttpStatus.OK)
  public List<IngredientDto> getAllRecipes(
    @PathVariable
      Long recipeId) {
    List<Ingredient> ingredients = ingredientService.getByRecipeId(recipeId);
    return ingredients.stream().map(this::convertToIngredientDto).collect(Collectors.toList());
  }

  private Ingredient convertToIngredientEntity(IngredientDto ingredienDto) {
    Ingredient ingredient = modelMapper.map(ingredienDto, Ingredient.class);
    return ingredient;
  }

  private IngredientDto convertToIngredientDto(Ingredient ingredient) {
    IngredientDto ingredienDto = modelMapper.map(ingredient, IngredientDto.class);
    return ingredienDto;
  }
}
