package com.readme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for README generation responses.
 * Contains the generated markdown content and operation status.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadmeResponse {

  /**
   * The generated README content in Markdown format.
   */
  private String markdown;

  /**
   * Status of the generation operation (e.g., "success", "error").
   */
  private String status;

  /**
   * Optional error message if the operation failed.
   */
  private String error;

  /**
   * Creates a successful response with the generated markdown.
   *
   * @param markdown the generated README markdown
   * @return a successful ReadmeResponse
   */
  public static ReadmeResponse success(String markdown) {
    return ReadmeResponse.builder()
        .markdown(markdown)
        .status("success")
        .build();
  }

  /**
   * Creates an error response with an error message.
   *
   * @param errorMessage the error message
   * @return an error ReadmeResponse
   */
  public static ReadmeResponse error(String errorMessage) {
    return ReadmeResponse.builder()
        .status("error")
        .error(errorMessage)
        .build();
  }
}
