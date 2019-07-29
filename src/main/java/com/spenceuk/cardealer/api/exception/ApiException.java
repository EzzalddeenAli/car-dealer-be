package com.spenceuk.cardealer.api.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  @Getter
  private ApiExceptions apiException;

  /**
   * Creates an Api Runtime Exception.
   * @param apiException exception type with message.
   */
  public ApiException(ApiExceptions apiException) {
    super(apiException.msg());
    this.apiException = apiException;
  }

  /**
   * HttpStatus Object.
   */
  public HttpStatus status() {
    return apiException.status();
  }

  /**
   * Status Code.
   */
  public int statusCode() {
    return apiException.code();
  }
}