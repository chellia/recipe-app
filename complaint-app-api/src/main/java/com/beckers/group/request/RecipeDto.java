package com.beckers.group.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

  @JsonIgnore
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  private Long id;

  @NotNull
  private String name;

  private String createdDateTime;

  private boolean vegetarian;

  private Integer servings;

  private String instructions;

  public LocalDateTime stringFormatToLocalDateTime(String createdDateTimeFormat) {
    return createdDateTimeFormat != null
      ? LocalDateTime.parse(createdDateTimeFormat, formatter)
      : null;
  }

  public String localDateTimeToStringFormat(LocalDateTime localDateTime) {
    return localDateTime != null ? localDateTime.format(formatter) : null;
  }

}
