package com.readme.service;

import com.readme.dto.ReadmeRequest;
import com.readme.exception.ReadmeGenerationException;
import com.readme.model.TemplateType;
import com.readme.util.BadgeGenerator;
import com.readme.util.MarkdownFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for generating README.md files from user requests.
 * Provides comprehensive README generation with template support and customization options.
 *
 * <p>This service handles:
 * <ul>
 *   <li>Template-based README generation</li>
 *   <li>Badge generation for technologies and repository stats</li>
 *   <li>Section formatting and organization</li>
 *   <li>Markdown content creation and validation</li>
 * </ul>
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ReadmeGeneratorService {

  private static final Logger logger = LoggerFactory.getLogger(ReadmeGeneratorService.class);
  
  private static final Map<TemplateType, List<String>> TEMPLATE_SECTIONS = new HashMap<>();
  
  static {
    TEMPLATE_SECTIONS.put(TemplateType.API, 
        List.of("Authentication", "Endpoints", "Examples", "Rate Limiting"));
    TEMPLATE_SECTIONS.put(TemplateType.LIBRARY, 
        List.of("Quick Start", "API Reference", "Examples"));
    TEMPLATE_SECTIONS.put(TemplateType.FRONTEND, 
        List.of("Demo", "Features", "Customization"));
    TEMPLATE_SECTIONS.put(TemplateType.CLI, 
        List.of("Commands", "Options", "Configuration"));
    TEMPLATE_SECTIONS.put(TemplateType.FULLSTACK, 
        List.of("Tech Stack", "Architecture", "Deployment"));
  }

  /**
   * Generates a complete README.md file based on the provided request.
   *
   * @param request the README generation request with all necessary information
   * @return the generated README content in Markdown format
   * @throws ReadmeGenerationException if generation fails
   */
  public String generateReadme(ReadmeRequest request) throws ReadmeGenerationException {
    try {
      logger.info("Generating README for project: {}", request.getProjectName());
      
      validateRequest(request);
      
      StringBuilder readme = new StringBuilder();
      
      // Build README sections in order
      readme.append(generateHeader(request));
      
      if (request.isIncludeBadges()) {
        readme.append(generateBadges(request));
      }
      
      if (request.isIncludeTableOfContents()) {
        readme.append(generateTableOfContents(request));
      }
      
      readme.append(generateDescription(request));
      
      if (request.isIncludeScreenshots()) {
        readme.append(generateScreenshotSection());
      }
      
      if (StringUtils.isNotBlank(request.getFeatures())) {
        readme.append(generateFeatures(request));
      }
      
      if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
        readme.append(generateTechStack(request));
      }
      
      if (StringUtils.isNotBlank(request.getInstallation())) {
        readme.append(generateInstallation(request));
      }
      
      if (StringUtils.isNotBlank(request.getUsage())) {
        readme.append(generateUsage(request));
      }
      
      if (request.getTemplateType() != null) {
        readme.append(generateTemplateSpecificSections(request));
      }
      
      if (request.isIncludeContributing()) {
        readme.append(generateContributing());
      }
      
      if (request.isIncludeLicense()) {
        readme.append(generateLicense(request));
      }
      
      readme.append(generateFooter(request));
      
      logger.info("README generated successfully for project: {}", request.getProjectName());
      return readme.toString();
      
    } catch (Exception e) {
      logger.error("Error generating README for project: {}", request.getProjectName(), e);
      throw new ReadmeGenerationException("Failed to generate README: " + e.getMessage(), e);
    }
  }

  /**
   * Validates the README generation request.
   *
   * @param request the request to validate
   * @throws ReadmeGenerationException if validation fails
   */
  private void validateRequest(ReadmeRequest request) throws ReadmeGenerationException {
    if (request == null) {
      throw new ReadmeGenerationException("Request cannot be null");
    }
    
    if (StringUtils.isBlank(request.getProjectName())) {
      throw new ReadmeGenerationException("Project name is required");
    }
    
    if (StringUtils.isBlank(request.getDescription())) {
      throw new ReadmeGenerationException("Project description is required");
    }
  }

  /**
   * Generates the header section with project name and tagline.
   *
   * @param request the README request
   * @return the formatted header section
   */
  private String generateHeader(ReadmeRequest request) {
    StringBuilder header = new StringBuilder();
    
    header.append(MarkdownFormatter.createCenteredBlock(
        MarkdownFormatter.createHeader(1, request.getProjectName())
    ));
    
    if (StringUtils.isNotBlank(request.getTagline())) {
      header.append(MarkdownFormatter.createCenteredBlock(
          "### " + request.getTagline() + "\n"
      ));
    }
    
    return header.toString();
  }

  /**
   * Generates the badges section.
   *
   * @param request the README request
   * @return the formatted badges section
   */
  private String generateBadges(ReadmeRequest request) {
    StringBuilder badges = new StringBuilder();
    badges.append("<div align=\"center\">\n\n");
    
    // GitHub badges
    if (StringUtils.isNotBlank(request.getRepositoryUrl())) {
      String repoPath = BadgeGenerator.extractRepoPath(request.getRepositoryUrl());
      if (StringUtils.isNotBlank(repoPath)) {
        badges.append(BadgeGenerator.generateStarsBadge(repoPath)).append("\n");
        badges.append(BadgeGenerator.generateForksBadge(repoPath)).append("\n");
        badges.append(BadgeGenerator.generateIssuesBadge(repoPath)).append("\n");
      }
    }
    
    // License badge
    if (StringUtils.isNotBlank(request.getLicense())) {
      badges.append(BadgeGenerator.generateLicenseBadge(request.getLicense())).append("\n");
    }
    
    // Technology badges
    if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
      for (String tech : request.getTechnologies()) {
        badges.append(BadgeGenerator.generateTechnologyBadge(tech)).append("\n");
      }
    }
    
    badges.append("\n</div>\n\n");
    return badges.toString();
  }

  /**
   * Generates the table of contents.
   *
   * @param request the README request
   * @return the formatted table of contents
   */
  private String generateTableOfContents(ReadmeRequest request) {
    StringBuilder toc = new StringBuilder();
    toc.append(MarkdownFormatter.createHeader(2, "Table of Contents"));
    
    toc.append(MarkdownFormatter.createTocLink("About")).append("\n");
    
    if (request.isIncludeScreenshots()) {
      toc.append(MarkdownFormatter.createTocLink("Screenshots")).append("\n");
    }
    if (StringUtils.isNotBlank(request.getFeatures())) {
      toc.append(MarkdownFormatter.createTocLink("Features")).append("\n");
    }
    if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
      toc.append(MarkdownFormatter.createTocLink("Tech Stack")).append("\n");
    }
    if (StringUtils.isNotBlank(request.getInstallation())) {
      toc.append(MarkdownFormatter.createTocLink("Installation")).append("\n");
    }
    if (StringUtils.isNotBlank(request.getUsage())) {
      toc.append(MarkdownFormatter.createTocLink("Usage")).append("\n");
    }
    if (request.isIncludeContributing()) {
      toc.append(MarkdownFormatter.createTocLink("Contributing")).append("\n");
    }
    if (request.isIncludeLicense()) {
      toc.append(MarkdownFormatter.createTocLink("License")).append("\n");
    }
    
    toc.append("\n");
    return toc.toString();
  }

  /**
   * Generates the description section.
   *
   * @param request the README request
   * @return the formatted description section
   */
  private String generateDescription(ReadmeRequest request) {
    StringBuilder desc = new StringBuilder();
    desc.append(MarkdownFormatter.createHeader(2, "About"));
    desc.append(request.getDescription()).append("\n\n");
    
    if (StringUtils.isNotBlank(request.getDemoUrl())) {
      desc.append(MarkdownFormatter.createBold(
          MarkdownFormatter.createLink("Live Demo", request.getDemoUrl())
      )).append("\n\n");
    }
    
    return desc.toString();
  }

  /**
   * Generates the screenshot section placeholder.
   *
   * @return the formatted screenshot section
   */
  private String generateScreenshotSection() {
    StringBuilder screenshots = new StringBuilder();
    screenshots.append(MarkdownFormatter.createHeader(2, "Screenshots"));
    screenshots.append(MarkdownFormatter.createImage(
        "App Screenshot", 
        "https://via.placeholder.com/800x400?text=Add+Your+Screenshot+Here"
    )).append("\n\n");
    return screenshots.toString();
  }

  /**
   * Generates the features section.
   *
   * @param request the README request
   * @return the formatted features section
   */
  private String generateFeatures(ReadmeRequest request) {
    StringBuilder features = new StringBuilder();
    features.append(MarkdownFormatter.createHeader(2, "Features"));
    features.append(MarkdownFormatter.createUnorderedListFromText(request.getFeatures()));
    return features.toString();
  }

  /**
   * Generates the tech stack section.
   *
   * @param request the README request
   * @return the formatted tech stack section
   */
  private String generateTechStack(ReadmeRequest request) {
    StringBuilder techStack = new StringBuilder();
    techStack.append(MarkdownFormatter.createHeader(2, "Tech Stack"));
    techStack.append(MarkdownFormatter.createUnorderedList(
        request.getTechnologies().stream()
            .map(tech -> MarkdownFormatter.createBold(tech))
            .toList()
    ));
    return techStack.toString();
  }

  /**
   * Generates the installation section.
   *
   * @param request the README request
   * @return the formatted installation section
   */
  private String generateInstallation(ReadmeRequest request) {
    StringBuilder installation = new StringBuilder();
    installation.append(MarkdownFormatter.createHeader(2, "Installation"));
    installation.append(MarkdownFormatter.createCodeBlock(request.getInstallation(), "bash"));
    return installation.toString();
  }

  /**
   * Generates the usage section.
   *
   * @param request the README request
   * @return the formatted usage section
   */
  private String generateUsage(ReadmeRequest request) {
    StringBuilder usage = new StringBuilder();
    usage.append(MarkdownFormatter.createHeader(2, "Usage"));
    usage.append(MarkdownFormatter.createCodeBlock(request.getUsage(), "bash"));
    return usage.toString();
  }

  /**
   * Generates template-specific sections based on the template type.
   *
   * @param request the README request
   * @return the formatted template-specific sections
   */
  private String generateTemplateSpecificSections(ReadmeRequest request) {
    StringBuilder sections = new StringBuilder();
    List<String> specificSections = TEMPLATE_SECTIONS.get(request.getTemplateType());
    
    if (specificSections != null) {
      for (String section : specificSections) {
        if (!isSectionAlreadyIncluded(section, request)) {
          sections.append(MarkdownFormatter.createHeader(2, section));
          sections.append("*Documentation coming soon...*\n\n");
        }
      }
    }
    
    return sections.toString();
  }

  /**
   * Checks if a section has already been included in the README.
   *
   * @param section the section name
   * @param request the README request
   * @return true if the section is already included
   */
  private boolean isSectionAlreadyIncluded(String section, ReadmeRequest request) {
    return (section.equals("Installation") && StringUtils.isNotBlank(request.getInstallation()))
        || (section.equals("Usage") && StringUtils.isNotBlank(request.getUsage()))
        || (section.equals("Features") && StringUtils.isNotBlank(request.getFeatures()));
  }

  /**
   * Generates the contributing section.
   *
   * @return the formatted contributing section
   */
  private String generateContributing() {
    return MarkdownFormatter.createHeader(2, "Contributing")
        + "Contributions are always welcome!\n\n"
        + "1. Fork the project\n"
        + "2. Create your feature branch (`git checkout -b feature/AmazingFeature`)\n"
        + "3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)\n"
        + "4. Push to the branch (`git push origin feature/AmazingFeature`)\n"
        + "5. Open a Pull Request\n\n";
  }

  /**
   * Generates the license section.
   *
   * @param request the README request
   * @return the formatted license section
   */
  private String generateLicense(ReadmeRequest request) {
    String license = StringUtils.isNotBlank(request.getLicense()) 
        ? request.getLicense() : "MIT";
    return MarkdownFormatter.createHeader(2, "License")
        + "This project is licensed under the " + license 
        + " License - see the [LICENSE](LICENSE) file for details.\n\n";
  }

  /**
   * Generates the footer section.
   *
   * @param request the README request
   * @return the formatted footer section
   */
  private String generateFooter(ReadmeRequest request) {
    StringBuilder footer = new StringBuilder();
    footer.append(MarkdownFormatter.createHorizontalRule());
    footer.append("<div align=\"center\">\n\n");
    
    if (StringUtils.isNotBlank(request.getAuthor())) {
      footer.append("Made by ")
            .append(MarkdownFormatter.createBold(request.getAuthor()))
            .append("\n\n");
    }
    
    if (StringUtils.isNotBlank(request.getRepositoryUrl())) {
      footer.append("Star this repo if you find it useful!\n\n");
    }
    
    footer.append("</div>\n");
    return footer.toString();
  }
}
