package com.task.common.exception;

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
  
  @ExceptionHandler(InvalidFIlter.class)
  public ResponseEntity<Object> handleInvalidFIlterExceptions(Exception ex) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ErrorResponse error = new ErrorResponse("Invalid FIlter", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }	
  
  @ExceptionHandler(SearchNotSupported.class)
  public ResponseEntity<Object> handleSearchNotSupportedExceptions(Exception ex) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ErrorResponse error = new ErrorResponse("Search Not Supported", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }	
  
  @ExceptionHandler(SortingNotSupported.class)
  public ResponseEntity<Object> handleSortingNotSupportedExceptions(Exception ex) {
    List<String> details = new ArrayList<>();
    details.add(ex.getLocalizedMessage());
    ErrorResponse error = new ErrorResponse("Sorting Not Supported", details);
    return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
  }	
}
