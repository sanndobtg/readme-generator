package com.readme.model;

/**
 * Enumeration of available README template types.
 * Each template type provides a predefined structure optimized for different project types.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public enum TemplateType {
  /**
   * Template for REST API projects.
   * Includes sections for authentication, endpoints, and API documentation.
   */
  API("API"),

  /**
   * Template for library/package projects.
   * Includes sections for installation, API reference, and usage examples.
   */
  LIBRARY("Library"),

  /**
   * Template for frontend application projects.
   * Includes sections for demo, screenshots, and UI features.
   */
  FRONTEND("Frontend"),

  /**
   * Template for command-line interface projects.
   * Includes sections for commands, options, and CLI usage.
   */
  CLI("CLI"),

  /**
   * Template for full-stack application projects.
   * Includes sections for both frontend and backend components.
   */
  FULLSTACK("Fullstack");

  private final String displayName;

  /**
   * Constructs a TemplateType with the specified display name.
   *
   * @param displayName the human-readable name of the template type
   */
  TemplateType(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Gets the display name of this template type.
   *
   * @return the display name
   */
  public String getDisplayName() {
    return displayName;
  }
}
