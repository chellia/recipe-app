package com.home.recipe.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RestErrorResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private int status;
  private String error;
  private String message;
  private String path;
}
