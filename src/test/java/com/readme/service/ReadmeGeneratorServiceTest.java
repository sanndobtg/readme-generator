package com.readme.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.readme.dto.ReadmeRequest;
import com.readme.exception.ReadmeGenerationException;
import com.readme.model.TemplateType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ReadmeGeneratorService}.
 * Tests README generation logic and template handling.
 *
 * @author README Generator Team
 * @version 1.0.0
 */
class ReadmeGeneratorServiceTest {

  private ReadmeGeneratorService service;

  @BeforeEach
  void setUp() {
    service = new ReadmeGeneratorService();
  }

  @Test
  @DisplayName("Should generate README with minimal required fields")
  void testGenerateReadmeMinimal() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("This is a test project")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).isNotEmpty();
    assertThat(readme).contains("Test Project");
    assertThat(readme).contains("This is a test project");
  }

  @Test
  @DisplayName("Should throw exception for null request")
  void testGenerateReadmeNullRequest() {
    assertThatThrownBy(() -> service.generateReadme(null))
        .isInstanceOf(ReadmeGenerationException.class)
        .hasMessageContaining("Request cannot be null");
  }

  @Test
  @DisplayName("Should throw exception for missing project name")
  void testGenerateReadmeMissingProjectName() {
    ReadmeRequest request = ReadmeRequest.builder()
        .description("Description")
        .build();

    assertThatThrownBy(() -> service.generateReadme(request))
        .isInstanceOf(ReadmeGenerationException.class)
        .hasMessageContaining("Project name is required");
  }

  @Test
  @DisplayName("Should throw exception for missing description")
  void testGenerateReadmeMissingDescription() {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Project")
        .build();

    assertThatThrownBy(() -> service.generateReadme(request))
        .isInstanceOf(ReadmeGenerationException.class)
        .hasMessageContaining("description is required");
  }

  @Test
  @DisplayName("Should include tagline when provided")
  void testGenerateReadmeWithTagline() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .tagline("A great tagline")
        .description("Description")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("A great tagline");
  }

  @Test
  @DisplayName("Should include badges when enabled")
  void testGenerateReadmeWithBadges() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .repositoryUrl("https://github.com/user/repo")
        .includeBadges(true)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("img.shields.io");
    assertThat(readme).contains("stars");
  }

  @Test
  @DisplayName("Should include table of contents when enabled")
  void testGenerateReadmeWithToc() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .includeTableOfContents(true)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Table of Contents");
    assertThat(readme).contains("About");
  }

  @Test
  @DisplayName("Should include features section")
  void testGenerateReadmeWithFeatures() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .features("Feature 1\nFeature 2\nFeature 3")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Features");
    assertThat(readme).contains("Feature 1");
    assertThat(readme).contains("Feature 2");
  }

  @Test
  @DisplayName("Should include tech stack section")
  void testGenerateReadmeWithTechStack() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .technologies(List.of("Java", "Spring Boot", "React"))
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Tech Stack");
    assertThat(readme).contains("Java");
    assertThat(readme).contains("Spring Boot");
    assertThat(readme).contains("React");
  }

  @Test
  @DisplayName("Should include installation section")
  void testGenerateReadmeWithInstallation() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .installation("npm install\nnpm start")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Installation");
    assertThat(readme).contains("npm install");
    assertThat(readme).contains("```bash");
  }

  @Test
  @DisplayName("Should include usage section")
  void testGenerateReadmeWithUsage() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .usage("npm run dev")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Usage");
    assertThat(readme).contains("npm run dev");
  }

  @Test
  @DisplayName("Should include template-specific sections for API template")
  void testGenerateReadmeWithApiTemplate() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("API Project")
        .description("Description")
        .templateType(TemplateType.API)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Authentication");
    assertThat(readme).contains("Endpoints");
  }

  @Test
  @DisplayName("Should include template-specific sections for Library template")
  void testGenerateReadmeWithLibraryTemplate() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Library Project")
        .description("Description")
        .templateType(TemplateType.LIBRARY)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Quick Start");
    assertThat(readme).contains("API Reference");
  }

  @Test
  @DisplayName("Should include contributing section when enabled")
  void testGenerateReadmeWithContributing() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .includeContributing(true)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Contributing");
    assertThat(readme).contains("Fork the project");
  }

  @Test
  @DisplayName("Should include license section when enabled")
  void testGenerateReadmeWithLicense() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .license("MIT")
        .includeLicense(true)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("License");
    assertThat(readme).contains("MIT");
  }

  @Test
  @DisplayName("Should include author in footer")
  void testGenerateReadmeWithAuthor() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .author("John Doe")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("John Doe");
    assertThat(readme).contains("Made with");
  }

  @Test
  @DisplayName("Should include demo URL")
  void testGenerateReadmeWithDemoUrl() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .demoUrl("https://demo.example.com")
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Live Demo");
    assertThat(readme).contains("https://demo.example.com");
  }

  @Test
  @DisplayName("Should include screenshot section when enabled")
  void testGenerateReadmeWithScreenshots() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Test Project")
        .description("Description")
        .includeScreenshots(true)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Screenshots");
    assertThat(readme).contains("placeholder");
  }

  @Test
  @DisplayName("Should generate complete README with all sections")
  void testGenerateReadmeComplete() throws ReadmeGenerationException {
    ReadmeRequest request = ReadmeRequest.builder()
        .projectName("Complete Project")
        .tagline("A comprehensive test")
        .description("Full description")
        .templateType(TemplateType.FULLSTACK)
        .technologies(List.of("Java", "React"))
        .features("Feature 1\nFeature 2")
        .installation("npm install")
        .usage("npm start")
        .repositoryUrl("https://github.com/user/repo")
        .demoUrl("https://demo.com")
        .license("MIT")
        .author("Test Author")
        .includeBadges(true)
        .includeTableOfContents(true)
        .includeContributing(true)
        .includeLicense(true)
        .includeScreenshots(true)
        .build();

    String readme = service.generateReadme(request);

    assertThat(readme).contains("Complete Project");
    assertThat(readme).contains("A comprehensive test");
    assertThat(readme).contains("Features");
    assertThat(readme).contains("Tech Stack");
    assertThat(readme).contains("Installation");
    assertThat(readme).contains("Usage");
    assertThat(readme).contains("Contributing");
    assertThat(readme).contains("License");
    assertThat(readme).contains("Test Author");
  }
}
