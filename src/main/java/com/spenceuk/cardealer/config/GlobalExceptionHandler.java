package com.spenceuk.cardealer.config;

import com.spenceuk.cardealer.api.exception.ApiException;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Global ApiException Handler.
   */
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ErrorMsg> apiError(ApiException ex, WebRequest req) {
    ErrorMsg error = new ErrorMsg();
    error.setTimestamp(LocalDateTime.now());
    error.setStatus(ex.statusCode());
    error.setError(ex.getMessage());
    return ResponseEntity.status(ex.status()).body(error);
  }


  @Getter
  @Setter
  class ErrorMsg {
    private int status;
    private String error;
    private LocalDateTime timestamp;
  }

}