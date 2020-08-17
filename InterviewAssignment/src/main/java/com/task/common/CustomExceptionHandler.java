package com.task.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

  @ExceptionHandler(StatusNotValid.class)
  public ResponseEntity<Object> handleStatusNotValidExceptions(Exception ex) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ErrorResponse error = new ErrorResponse("Validation Failed", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }	
}
