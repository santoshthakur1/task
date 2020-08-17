package com.task.common.exception;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
  public ErrorResponse(String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
  }

  private String message;

  private List<String> details;
}
