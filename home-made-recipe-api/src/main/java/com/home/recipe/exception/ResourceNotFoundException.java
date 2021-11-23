package com.home.recipe.exception;

public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -5218143265247846948L;

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
