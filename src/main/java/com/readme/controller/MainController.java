package com.readme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main web controller for serving the application's web interface.
 * Handles routing for the main HTML pages.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Controller
public class MainController {

  /**
   * Serves the main application page.
   *
   * @return the name of the index template
   */
  @GetMapping("/")
  public String index() {
    return "index";
  }

  /**
   * Serves the health check page for monitoring.
   *
   * @return the name of the health template
   */
  @GetMapping("/health")
  public String health() {
    return "health";
  }
}
