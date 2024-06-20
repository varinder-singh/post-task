package com.amplify.posttask.exception;

import com.amplify.posttask.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = { PostTaskException.class })
  // @ResponseBody
  // @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handleException(PostTaskException ex) {
    return ResponseEntity
        .status(ex.getHttpStatusCode())
        .body(new ErrorDto(ex.getErrorMessage()));
  }
}
