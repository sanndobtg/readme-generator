package com.readme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for GitHub export requests.
 * Contains the information needed to export a README to GitHub.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubExportRequest {

  /**
   * The GitHub repository URL.
   * Must be a valid GitHub repository URL.
   */
  @NotBlank(message = "Repository URL is required")
  @Pattern(
      regexp = "^https?://github\\.com/[\\w-]+/[\\w-]+.*$",
      message = "Must be a valid GitHub repository URL"
  )
  private String repositoryUrl;

  /**
   * The README markdown content to export.
   */
  @NotBlank(message = "README content is required")
  private String readmeContent;

  /**
   * GitHub personal access token for authentication.
   * Must have 'repo' scope permissions.
   */
  @NotBlank(message = "GitHub token is required")
  private String githubToken;
}
