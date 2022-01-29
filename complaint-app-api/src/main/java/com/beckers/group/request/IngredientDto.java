package com.beckers.group.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

  private Long id;

  @NotNull
  private String name;

  private double quantity;

  private String units;

  RecipeDto recipeDto;

  @Override
  public String toString() {
    return quantity + " " + units + " of " + name;
  }
}
