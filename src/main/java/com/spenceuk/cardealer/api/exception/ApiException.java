package com.spenceuk.cardealer.api.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String NO_CONTEXT = ".";
  private static final String NOT_EQUAL = " != ";

  private ApiExceptions apiException;

  /**
   * Creates an Api Runtime Exception with no context.
   * @param apiException exception type with message.
   */
  ApiException(ApiExceptions apiException) {
    this(apiException, NO_CONTEXT);
  }

  /**
   * Creates an Api Runtime Exception with additonal context.
   * use throwable message to add context.
   *
   * @param apiException exception type with message.
   * @param context additional context to add to the message.
   */
  ApiException(ApiExceptions apiException, String context) {
    super(apiException.msg() + ": " + context);
    this.apiException = apiException;
  }

  /**
   * Api Exception for no ID being present when required.
   * @return Status 400 Bad Request.
   */
  public static ApiException noId() {
    return new ApiException(ApiExceptions.ID_NOT_FOUND);
  }

  /**
   * Api Exception ID not found.
   * @param id the ID not found.
   * @return Status 404 Not Found.
   */
  public static ApiException idNotFound(long id) {
    return new ApiException(ApiExceptions.ID_NOT_FOUND, String.valueOf(id));
  }

  /**
   * Create ID mismatch API Exception.
   *
   * <p>Path id != Object id.
   * @param pathId id present in the URL path.
   * @param objectId id present in the request bodies object.
   * @return Status 400 Bad Request.
   */
  public static ApiException idMismatch(long pathId, long objectId) {
    var context = new StringBuilder("Path id: ")
        .append(pathId).append(NOT_EQUAL).append("Object: id ")
        .append(objectId).toString();
    return new ApiException(ApiExceptions.ID_MISMATCH, context);
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