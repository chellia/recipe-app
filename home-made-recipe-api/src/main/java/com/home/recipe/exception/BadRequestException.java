package com.home.recipe.exception;

public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 6171978443681353425L;

  public BadRequestException(String message) {
    super(message);
  }
}
