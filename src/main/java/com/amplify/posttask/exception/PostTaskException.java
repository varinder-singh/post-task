package com.amplify.posttask.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PostTaskException extends RuntimeException {
  private int httpStatusCode;
  private String errorMessage;

  public PostTaskException(HttpStatus httpStatus, String errorMessage) {
    super(errorMessage);
    this.httpStatusCode = httpStatus.value();
    this.errorMessage = errorMessage;
  }
}
