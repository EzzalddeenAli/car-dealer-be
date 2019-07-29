package com.spenceuk.cardealer.api.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public enum ApiExceptions {
  MISSING_ID(BAD_REQUEST, "Missing ID"),
  ID_MISMATCH(BAD_REQUEST, "ID's do not match"),
  ID_NOT_FOUND(NOT_FOUND, "Cannot find ID");

  private String msg;
  private HttpStatus status;

  ApiExceptions(HttpStatus status, String msg) {
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