package com.readme.exception;

/**
 * Exception thrown when README generation fails.
 * This is a checked exception that should be handled by the calling code.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public class ReadmeGenerationException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message the detail message
   */
  public ReadmeGenerationException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of the exception
   */
  public ReadmeGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}
