package com.example.demo.controller.errors;

import javax.management.InstanceNotFoundException;
import javax.xml.bind.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler extends ResponseEntityExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(InstanceNotFoundException.class)
  public ResponseEntity<String> handleNoHeroFoundException(
      InstanceNotFoundException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleHeroNoValidException(
      ValidationException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.PRECONDITION_FAILED)
        .body(exception.getMessage());
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(
      ValidationException exception
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }
}
