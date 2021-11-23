package com.home.recipe.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeUpdateDto {

  private String name;

  private boolean vegetarian;

  private Integer servings;

  private String instructions;

}
