package com.beckers.group.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

  private static final long serialVersionUID = 7246983447306271525L;

  public ResourceAlreadyExistsException(String message) {
    super(message);
  }
}
