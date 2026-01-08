package com.readme.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for generating GitHub README badges.
 * Provides methods to create various types of shields.io badges.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public final class BadgeGenerator {

  private static final String SHIELDS_IO_BASE = "https://img.shields.io";
  private static final String BADGE_STYLE = "for-the-badge";
  
  private static final Map<String, String> TECH_COLORS = new HashMap<>();
  private static final Map<String, String> TECH_LOGOS = new HashMap<>();

  static {
    // Initialize technology colors
    TECH_COLORS.put("Java", "ED8B00");
    TECH_COLORS.put("Spring", "6DB33F");
    TECH_COLORS.put("Spring Boot", "6DB33F");
    TECH_COLORS.put("JavaScript", "F7DF1E");
    TECH_COLORS.put("TypeScript", "3178C6");
    TECH_COLORS.put("React", "61DAFB");
    TECH_COLORS.put("Vue", "4FC08D");
    TECH_COLORS.put("Angular", "DD0031");
    TECH_COLORS.put("Python", "3776AB");
    TECH_COLORS.put("Node.js", "339933");
    TECH_COLORS.put("Go", "00ADD8");
    TECH_COLORS.put("Rust", "000000");
    TECH_COLORS.put("PHP", "777BB4");
    TECH_COLORS.put("Ruby", "CC342D");
    TECH_COLORS.put("C#", "239120");
    TECH_COLORS.put(".NET", "512BD4");

    // Initialize technology logos
    TECH_LOGOS.put("Java", "java");
    TECH_LOGOS.put("Spring", "spring");
    TECH_LOGOS.put("Spring Boot", "springboot");
    TECH_LOGOS.put("JavaScript", "javascript");
    TECH_LOGOS.put("TypeScript", "typescript");
    TECH_LOGOS.put("React", "react");
    TECH_LOGOS.put("Vue", "vuedotjs");
    TECH_LOGOS.put("Angular", "angular");
    TECH_LOGOS.put("Python", "python");
    TECH_LOGOS.put("Node.js", "nodedotjs");
    TECH_LOGOS.put("Go", "go");
    TECH_LOGOS.put("Rust", "rust");
    TECH_LOGOS.put("PHP", "php");
    TECH_LOGOS.put("Ruby", "ruby");
    TECH_LOGOS.put("C#", "csharp");
    TECH_LOGOS.put(".NET", "dotnet");
  }

  private BadgeGenerator() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Generates a GitHub stars badge for a repository.
   *
   * @param repoPath the repository path (owner/repo)
   * @return the markdown badge string
   */
  public static String generateStarsBadge(String repoPath) {
    if (StringUtils.isBlank(repoPath)) {
      return "";
    }
    return String.format("![GitHub stars](%s/github/stars/%s?style=%s)",
        SHIELDS_IO_BASE, repoPath, BADGE_STYLE);
  }

  /**
   * Generates a GitHub forks badge for a repository.
   *
   * @param repoPath the repository path (owner/repo)
   * @return the markdown badge string
   */
  public static String generateForksBadge(String repoPath) {
    if (StringUtils.isBlank(repoPath)) {
      return "";
    }
    return String.format("![GitHub forks](%s/github/forks/%s?style=%s)",
        SHIELDS_IO_BASE, repoPath, BADGE_STYLE);
  }

  /**
   * Generates a GitHub issues badge for a repository.
   *
   * @param repoPath the repository path (owner/repo)
   * @return the markdown badge string
   */
  public static String generateIssuesBadge(String repoPath) {
    if (StringUtils.isBlank(repoPath)) {
      return "";
    }
    return String.format("![GitHub issues](%s/github/issues/%s?style=%s)",
        SHIELDS_IO_BASE, repoPath, BADGE_STYLE);
  }

  /**
   * Generates a license badge.
   *
   * @param license the license type (e.g., MIT, Apache-2.0)
   * @return the markdown badge string
   */
  public static String generateLicenseBadge(String license) {
    if (StringUtils.isBlank(license)) {
      return "";
    }
    return String.format("![License](%s/badge/license-%s-blue?style=%s)",
        SHIELDS_IO_BASE, license.replace(" ", "_"), BADGE_STYLE);
  }

  /**
   * Generates a technology badge with appropriate color and logo.
   *
   * @param technology the technology name
   * @return the markdown badge string
   */
  public static String generateTechnologyBadge(String technology) {
    if (StringUtils.isBlank(technology)) {
      return "";
    }

    String color = TECH_COLORS.getOrDefault(technology, "0078D4");
    String logo = TECH_LOGOS.getOrDefault(technology, 
        technology.toLowerCase().replace(" ", "").replace(".", ""));

    return String.format("![%s](%s/badge/%s-%s?style=%s&logo=%s&logoColor=white)",
        technology, SHIELDS_IO_BASE, technology.replace(" ", "_"), 
        color, BADGE_STYLE, logo);
  }

  /**
   * Extracts the repository path (owner/repo) from a GitHub URL.
   *
   * @param url the full GitHub repository URL
   * @return the repository path or empty string if invalid
   */
  public static String extractRepoPath(String url) {
    if (StringUtils.isBlank(url)) {
      return "";
    }

    String cleaned = url.replace("https://github.com/", "")
                        .replace("http://github.com/", "")
                        .replace(".git", "");

    if (cleaned.endsWith("/")) {
      cleaned = cleaned.substring(0, cleaned.length() - 1);
    }

    return cleaned;
  }
}
