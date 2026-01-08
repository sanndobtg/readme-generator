package com.readme.exception;

/**
 * Exception thrown when GitHub export operations fail.
 * This includes authentication failures, network errors, and API errors.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public class GitHubExportException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message the detail message
   */
  public GitHubExportException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public GitHubExportException(String message, Throwable cause) {
    super(message, cause);
  }
}
