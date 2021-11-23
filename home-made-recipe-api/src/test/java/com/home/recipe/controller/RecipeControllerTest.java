package com.home.recipe.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.recipe.repository.IngredientRepository;
import com.home.recipe.repository.RecipeRepository;
import com.home.recipe.request.IngredientDto;
import com.home.recipe.request.RecipeDto;
import com.home.recipe.request.RecipeUpdateDto;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
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
class RecipeControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper mapper;

  @Test
  @DisplayName("When recipes are requested then return all recipes")
  void recipeRequest() throws Exception {
    mockMvc
        .perform(get("/recipes").with(csrf()).with(user("guest").password("guest")))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  @DisplayName("When recipes are requested by id then return corresponding recipe")
  void recipeRequestedById() throws Exception {
    mockMvc
        .perform(get("/recipes/1").with(csrf()).with(user("guest").password("guest")))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  @DisplayName("When recipes are requested by unknown id then return 404(NotFound)")
  void recipeRequestedByUnknownId() throws Exception {
    mockMvc
        .perform(get("/recipes/9").with(csrf()).with(user("guest").password("guest")))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @DisplayName("When recipe is deleted by id then return successful response")
  void recipeDeleteById() throws Exception {
    mockMvc
        .perform(delete("/recipes/1").with(csrf()).with(user("guest").password("guest")))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  @DisplayName("When recipe is deleted by unknown id then return 404(NotFound)")
  void recipeDeleteByUnknownId() throws Exception {
    mockMvc
        .perform(delete("/recipes/9").with(csrf()).with(user("guest").password("guest")))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @DisplayName("When a recipe creation is requested then it is persisted")
  void recipeCreatedSuccess() throws Exception {
    RecipeDto newRecipe = RecipeDto
        .builder()
        .name("Test Recipe")
        .build();

    RecipeDto recipeDto = mapper.readValue(mockMvc
        .perform(post("/recipes")
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newRecipe)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), RecipeDto.class);

    newRecipe.setId(recipeDto.getId()); // Populate the ID of the recipe after successful creation
    newRecipe.setCreatedDateTime(recipeDto.getCreatedDateTime());

    RecipeDto persistedRecipeDto = mapper.readValue(mockMvc
        .perform(get("/recipes/" + recipeDto.getId())
            .with(csrf())
            .with(user("guest").password("guest")))
        .andExpect(status().is2xxSuccessful())
        .andReturn()
        .getResponse()
        .getContentAsString(), RecipeDto.class);

    assertThat(persistedRecipeDto.getId(), equalTo(newRecipe.getId()));
    assertThat(persistedRecipeDto.getCreatedDateTime(), equalTo(newRecipe.getCreatedDateTime()));
  }

  @Test
  @DisplayName("When a recipe creation is requested twice then throws conflict")
  void recipeCreatedTwice() throws Exception {
    RecipeDto newRecipe = RecipeDto
        .builder()
        .name("Test Recipe")
        .build();

    RecipeDto recipeDto = mapper.readValue(mockMvc
        .perform(post("/recipes")
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newRecipe)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), RecipeDto.class);

    newRecipe.setId(recipeDto.getId());
    newRecipe.setCreatedDateTime(recipeDto.getCreatedDateTime());

    mockMvc
        .perform(post("/recipes")
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newRecipe)))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("When a recipe is updated then it is persisted")
  void recipeFullUpdate() throws Exception {
    RecipeDto newRecipe = RecipeDto
        .builder()
        .name("Test Recipe")
        .build();

    RecipeDto recipeDto = mapper.readValue(mockMvc
        .perform(post("/recipes")
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newRecipe)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), RecipeDto.class);

    RecipeUpdateDto recipeUpdateDto = RecipeUpdateDto
        .builder()
        .name("Updated Name")
        .servings(5)
        .vegetarian(true)
        .instructions("updated instructions")
        .build();

    RecipeDto updatedRecipeDto = mapper.readValue(mockMvc
        .perform(put("/recipes/" + recipeDto.getId())
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(recipeUpdateDto)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(), RecipeDto.class);

    assertThat(updatedRecipeDto.getName(), equalTo("Updated Name"));
    assertThat(updatedRecipeDto.getServings(), equalTo(5));
    assertThat(updatedRecipeDto.isVegetarian(), equalTo(true));
    assertThat(updatedRecipeDto.getInstructions(), equalTo("updated instructions"));
  }

  @Test
  @DisplayName("When a unknown recipe is updated then returns NotFound")
  void recipeUnknowUpdate() throws Exception {
    RecipeUpdateDto recipeUpdateDto = RecipeUpdateDto.builder().build();
    mockMvc
        .perform(put("/recipes/" + 9)
            .with(csrf())
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(recipeUpdateDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("When a recipe is updated without token then returns NotFound")
  void recipeUpdateWithOutToken() throws Exception {
    RecipeDto newRecipe = RecipeDto
        .builder()
        .name("Test Recipe")
        .build();

    mockMvc
        .perform(post("/recipes")
            .with(user("guest").password("guest"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(newRecipe)))
        .andExpect(status().isForbidden());
  }

}
