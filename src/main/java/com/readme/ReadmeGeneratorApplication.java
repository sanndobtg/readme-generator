package com.readme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the README Generator.
 * This class bootstraps the Spring Boot application.
 *
 * <p>The application provides:
 * <ul>
 *   <li>Web interface for README generation</li>
 *   <li>REST API for programmatic access</li>
 *   <li>GitHub integration for direct export</li>
 * </ul>
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class ReadmeGeneratorApplication {

  /**
   * Main entry point for the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(ReadmeGeneratorApplication.class, args);
  }
}
