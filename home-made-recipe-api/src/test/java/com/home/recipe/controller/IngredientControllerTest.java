package com.home.recipe.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.recipe.request.IngredientDto;
import com.home.recipe.request.RecipeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
     scripts = "classpath:data.sql")
class IngredientControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  @DisplayName("When ingredients are requested for a recipe then return all ingredients")
  void allIngredientRequestByRecipeId() throws Exception {
    mockMvc
        .perform(get("/ingredients/recipe/1").with(csrf()).with(user("guest").password("guest")))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$", hasSize(5)));
  }

  @Test
  @DisplayName("When a ingredient creation is requested then it is persisted")
  void ingredientCreatedSuccess() throws Exception {

    RecipeDto existingRecipeDto = mapper.readValue(mockMvc
            .perform(get("/recipes/1")
                .with(csrf())
                .with(user("guest").password("guest")))
            .andExpect(status().is2xxSuccessful())
            .andReturn()
            .getResponse()
            .getContentAsString(), RecipeDto.class);

    IngredientDto newIngredientDto = IngredientDto
        .builder()
        .name("Tomoto")
        .recipeDto(existingRecipeDto)
        .build();

    //add ingredient
    IngredientDto ingredientDto = mapper.readValue(mockMvc
        .perform(post("/ingredients")
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newIngredientDto)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), IngredientDto.class);

    IngredientDto persistedIngredientDto = mapper.readValue(mockMvc
        .perform(get("/ingredients/" + ingredientDto.getId())
            .with(csrf())
            .with(user("guest").password("guest")))
        .andExpect(status().is2xxSuccessful())
        .andReturn()
        .getResponse()
        .getContentAsString(), IngredientDto.class);

    assertThat(persistedIngredientDto.getId(), equalTo(ingredientDto.getId()));

  }


}
