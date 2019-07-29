package com.spenceuk.cardealer.api.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

public enum ApiExceptions {
  MISSING_ID("Missing ID", BAD_REQUEST);

  private String msg;
  private HttpStatus status;

  ApiExceptions(String msg, HttpStatus status) {
    this.msg = status.getReasonPhrase() + ": " + msg;
    this.status = status;
  }

  /**
   * Human readable exception message.
   * @return exception message as a String.
   */
  public String msg() {
    return msg;
  }

  /**
   * Http status Object.
   * @return status Object.
   */
  public HttpStatus status() {
    return status;
  }

  /**
   * Http status code.
   * @return status code.
   */
  public int code() {
    return status.value();
  }
}