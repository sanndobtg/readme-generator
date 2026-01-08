package com.readme.controller;

import com.readme.dto.GitHubExportRequest;
import com.readme.dto.ReadmeRequest;
import com.readme.dto.ReadmeResponse;
import com.readme.exception.GitHubExportException;
import com.readme.exception.ReadmeGenerationException;
import com.readme.model.TemplateType;
import com.readme.service.GitHubService;
import com.readme.service.ReadmeGeneratorService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for README generation and export operations.
 * Provides endpoints for generating README content and exporting to GitHub.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>POST /api/generate - Generate README markdown</li>
 *   <li>POST /api/export - Export README to GitHub</li>
 *   <li>GET /api/validate-token - Validate GitHub token</li>
 *   <li>GET /api/templates - Get available templates and options</li>
 * </ul>
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ReadmeApiController {

  private static final Logger logger = LoggerFactory.getLogger(ReadmeApiController.class);

  private final ReadmeGeneratorService readmeGeneratorService;
  private final GitHubService githubService;

  /**
   * Constructs the API controller with required services.
   *
   * @param readmeGeneratorService service for README generation
   * @param githubService service for GitHub operations
   */
  public ReadmeApiController(ReadmeGeneratorService readmeGeneratorService,
                             GitHubService githubService) {
    this.readmeGeneratorService = readmeGeneratorService;
    this.githubService = githubService;
  }

  /**
   * Generates README markdown from the provided request.
   *
   * @param request the README generation request
   * @return response containing the generated markdown
   */
  @PostMapping("/generate")
  public ResponseEntity<ReadmeResponse> generateReadme(@Valid @RequestBody ReadmeRequest request) {
    try {
      logger.info("Received README generation request for project: {}", 
          request.getProjectName());

      String markdown = readmeGeneratorService.generateReadme(request);
      return ResponseEntity.ok(ReadmeResponse.success(markdown));

    } catch (ReadmeGenerationException e) {
      logger.error("README generation failed", e);
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(ReadmeResponse.error(e.getMessage()));
    }
  }

  /**
   * Exports README content to a GitHub repository.
   *
   * @param exportRequest the export request containing repository details and content
   * @return response containing the operation result
   */
  @PostMapping("/export")
  public ResponseEntity<Map<String, String>> exportToGitHub(
      @Valid @RequestBody GitHubExportRequest exportRequest) {
    try {
      logger.info("Received GitHub export request for repository: {}", 
          exportRequest.getRepositoryUrl());

      String result = githubService.exportToGitHub(
          exportRequest.getRepositoryUrl(),
          exportRequest.getReadmeContent(),
          exportRequest.getGithubToken()
      );

      Map<String, String> response = new HashMap<>();
      response.put("message", result);
      response.put("status", "success");

      return ResponseEntity.ok(response);

    } catch (GitHubExportException e) {
      logger.error("GitHub export failed", e);
      
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", e.getMessage());
      errorResponse.put("status", "error");
      
      return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(errorResponse);
    }
  }

  /**
   * Validates a GitHub personal access token.
   *
   * @param token the token to validate
   * @return response indicating whether the token is valid
   */
  @GetMapping("/validate-token")
  public ResponseEntity<Map<String, Boolean>> validateToken(@RequestParam String token) {
    try {
      boolean isValid = githubService.validateToken(token);
      Map<String, Boolean> response = new HashMap<>();
      response.put("valid", isValid);
      return ResponseEntity.ok(response);
      
    } catch (GitHubExportException e) {
      logger.error("Token validation failed", e);
      Map<String, Boolean> response = new HashMap<>();
      response.put("valid", false);
      return ResponseEntity.ok(response);
    }
  }

  /**
   * Retrieves available templates, technologies, and licenses.
   *
   * @return response containing all available options
   */
  @GetMapping("/templates")
  public ResponseEntity<Map<String, Object>> getTemplates() {
    Map<String, Object> templates = new HashMap<>();

    String[] types = new String[TemplateType.values().length];
    for (int i = 0; i < TemplateType.values().length; i++) {
      types[i] = TemplateType.values()[i].name();
    }
    templates.put("types", types);

    templates.put("technologies", new String[]{
        "Java", "Spring Boot", "JavaScript", "React", "Vue", "Angular",
        "Python", "Django", "Flask", "Node.js", "Express", "TypeScript",
        "Go", "Rust", "PHP", "Laravel", "Ruby", "Rails", "C#", ".NET"
    });

    templates.put("licenses", new String[]{
        "MIT", "Apache-2.0", "GPL-3.0", "BSD-3-Clause", "ISC"
    });

    return ResponseEntity.ok(templates);
  }

  /**
   * Handles validation exceptions and returns error details.
   *
   * @param ex the validation exception
   * @return response containing field-specific error messages
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    Map<String, Object> response = new HashMap<>();
    response.put("status", "error");
    response.put("errors", errors);

    return ResponseEntity.badRequest().body(response);
  }
}
