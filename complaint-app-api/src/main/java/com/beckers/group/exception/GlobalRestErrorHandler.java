package com.beckers.group.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalRestErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<RestErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex,
      HttpServletRequest request) {

    RestErrorResponse restError = RestErrorResponse
        .builder()
        .error("Not Found")
        .message(ex.getMessage())
        .status(HttpStatus.NOT_FOUND.value())
        .path(request.getRequestURI())
        .build();

    return new ResponseEntity<>(restError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<RestErrorResponse> handleResourceAlreadyExistsException(
      ResourceAlreadyExistsException ex,
      HttpServletRequest request) {

    RestErrorResponse restError = RestErrorResponse
        .builder()
        .error("Duplicate")
        .message(ex.getMessage())
        .status(HttpStatus.CONFLICT.value())
        .path(request.getRequestURI())
        .build();

    return new ResponseEntity<>(restError, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<RestErrorResponse> handleBadRequestException(
      BadRequestException ex,
      HttpServletRequest request) {

    RestErrorResponse restErrorResponse = RestErrorResponse
        .builder()
        .error("Invalid Data")
        .message(ex.getMessage())
        .status(HttpStatus.BAD_REQUEST.value())
        .path(request.getRequestURI())
        .build();

    return new ResponseEntity<>(restErrorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestErrorResponse> handleInternalServerError(
      Exception ex,
      HttpServletRequest request) {

    RestErrorResponse restErrorResponse = RestErrorResponse
        .builder()
        .error("Internal Server Error")
        .message(ex.getMessage())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .path(request.getRequestURI())
        .build();

    return new ResponseEntity<>(restErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
