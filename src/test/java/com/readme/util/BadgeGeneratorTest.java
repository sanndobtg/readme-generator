package com.readme.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link BadgeGenerator}.
 * Tests badge generation functionality for various badge types.
 *
 * @author README Generator Team
 * @version 1.0.0
 */
class BadgeGeneratorTest {

  @Test
  @DisplayName("Should generate valid stars badge for repository")
  void testGenerateStarsBadge() {
    String badge = BadgeGenerator.generateStarsBadge("owner/repo");
    
    assertThat(badge).isNotEmpty();
    assertThat(badge).contains("img.shields.io");
    assertThat(badge).contains("owner/repo");
    assertThat(badge).contains("stars");
  }

  @Test
  @DisplayName("Should return empty string for null repository path")
  void testGenerateStarsBadgeWithNull() {
    String badge = BadgeGenerator.generateStarsBadge(null);
    assertThat(badge).isEmpty();
  }

  @Test
  @DisplayName("Should generate valid forks badge")
  void testGenerateForksBadge() {
    String badge = BadgeGenerator.generateForksBadge("owner/repo");
    
    assertThat(badge).isNotEmpty();
    assertThat(badge).contains("forks");
  }

  @Test
  @DisplayName("Should generate valid issues badge")
  void testGenerateIssuesBadge() {
    String badge = BadgeGenerator.generateIssuesBadge("owner/repo");
    
    assertThat(badge).isNotEmpty();
    assertThat(badge).contains("issues");
  }

  @Test
  @DisplayName("Should generate valid license badge")
  void testGenerateLicenseBadge() {
    String badge = BadgeGenerator.generateLicenseBadge("MIT");
    
    assertThat(badge).isNotEmpty();
    assertThat(badge).contains("license");
    assertThat(badge).contains("MIT");
  }

  @Test
  @DisplayName("Should generate technology badge with correct color")
  void testGenerateTechnologyBadge() {
    String badge = BadgeGenerator.generateTechnologyBadge("Java");
    
    assertThat(badge).isNotEmpty();
    assertThat(badge).contains("Java");
    assertThat(badge).contains("ED8B00");
  }

  @Test
  @DisplayName("Should use default color for unknown technology")
  void testGenerateTechnologyBadgeUnknown() {
    String badge = BadgeGenerator.generateTechnologyBadge("UnknownTech");
    
    assertThat(badge).isNotEmpty();
    assertThat(badge).contains("UnknownTech");
    assertThat(badge).contains("0078D4");
  }

  @Test
  @DisplayName("Should extract repository path from GitHub URL")
  void testExtractRepoPath() {
    String path = BadgeGenerator.extractRepoPath("https://github.com/owner/repo");
    assertThat(path).isEqualTo("owner/repo");
  }

  @Test
  @DisplayName("Should handle GitHub URL with trailing slash")
  void testExtractRepoPathWithTrailingSlash() {
    String path = BadgeGenerator.extractRepoPath("https://github.com/owner/repo/");
    assertThat(path).isEqualTo("owner/repo");
  }

  @Test
  @DisplayName("Should handle GitHub URL with .git extension")
  void testExtractRepoPathWithGitExtension() {
    String path = BadgeGenerator.extractRepoPath("https://github.com/owner/repo.git");
    assertThat(path).isEqualTo("owner/repo");
  }

  @Test
  @DisplayName("Should return empty string for null URL")
  void testExtractRepoPathWithNull() {
    String path = BadgeGenerator.extractRepoPath(null);
    assertThat(path).isEmpty();
  }
}
