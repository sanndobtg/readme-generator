package com.readme.dto;

import com.readme.model.TemplateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for README generation requests.
 * Contains all the information needed to generate a professional README.md file.
 *
 * <p>This class uses Bean Validation annotations to ensure data integrity
 * and provides builder pattern for convenient object creation.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadmeRequest {

  /**
   * The name of the project.
   * This will be displayed as the main title in the README.
   */
  @NotBlank(message = "Project name is required")
  @Size(min = 1, max = 100, message = "Project name must be between 1 and 100 characters")
  private String projectName;

  /**
   * A short, catchy tagline for the project.
   * Appears below the project name as a subtitle.
   */
  @Size(max = 200, message = "Tagline must not exceed 200 characters")
  private String tagline;

  /**
   * Detailed description of the project.
   * Should explain what the project does and why it exists.
   */
  @NotBlank(message = "Description is required")
  @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
  private String description;

  /**
   * The type of template to use for README generation.
   * Determines which sections and structure will be included.
   */
  private TemplateType templateType;

  /**
   * List of technologies/frameworks used in the project.
   * Used for generating technology badges and tech stack section.
   */
  @Builder.Default
  private List<String> technologies = new ArrayList<>();

  /**
   * Multi-line string listing the project's key features.
   * Each line will be formatted as a bullet point in the README.
   */
  @Size(max = 1000, message = "Features must not exceed 1000 characters")
  private String features;

  /**
   * Installation instructions or commands.
   * Will be displayed in a code block in the Installation section.
   */
  @Size(max = 1000, message = "Installation instructions must not exceed 1000 characters")
  private String installation;

  /**
   * Usage examples or commands.
   * Will be displayed in a code block in the Usage section.
   */
  @Size(max = 1000, message = "Usage examples must not exceed 1000 characters")
  private String usage;

  /**
   * The type of license for the project (e.g., MIT, Apache-2.0).
   */
  @Size(max = 50, message = "License must not exceed 50 characters")
  private String license;

  /**
   * The name or GitHub username of the project author.
   */
  @Size(max = 100, message = "Author name must not exceed 100 characters")
  private String author;

  /**
   * Full URL to the GitHub repository.
   * Used for generating GitHub badges and links.
   */
  @Pattern(
      regexp = "^(https?://github\\.com/[\\w-]+/[\\w-]+)?$",
      message = "Repository URL must be a valid GitHub URL"
  )
  private String repositoryUrl;

  /**
   * URL to a live demo of the project.
   */
  @Pattern(
      regexp = "^(https?://.*)?$",
      message = "Demo URL must be a valid HTTP(S) URL"
  )
  private String demoUrl;

  /**
   * Whether to include badges (stars, forks, license, etc.) in the README.
   */
  @Builder.Default
  private boolean includeBadges = true;

  /**
   * Whether to include a table of contents in the README.
   */
  @Builder.Default
  private boolean includeTableOfContents = false;

  /**
   * Whether to include a Contributing section in the README.
   */
  @Builder.Default
  private boolean includeContributing = true;

  /**
   * Whether to include a License section in the README.
   */
  @Builder.Default
  private boolean includeLicense = true;

  /**
   * Whether to include a Screenshots section placeholder in the README.
   */
  @Builder.Default
  private boolean includeScreenshots = false;

  /**
   * Custom sections in JSON or structured format.
   * Allows users to add additional custom sections to the README.
   */
  private String customSections;
}
