package com.readme.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MarkdownFormatter}.
 * Tests markdown formatting functionality.
 *
 * @author README Generator Team
 * @version 1.0.0
 */
class MarkdownFormatterTest {

  @Test
  @DisplayName("Should create header with correct level")
  void testCreateHeader() {
    String header = MarkdownFormatter.createHeader(1, "Test Header");
    assertThat(header).isEqualTo("# Test Header\n\n");
  }

  @Test
  @DisplayName("Should create level 2 header")
  void testCreateHeaderLevel2() {
    String header = MarkdownFormatter.createHeader(2, "Level 2");
    assertThat(header).isEqualTo("## Level 2\n\n");
  }

  @Test
  @DisplayName("Should throw exception for invalid header level")
  void testCreateHeaderInvalidLevel() {
    assertThatThrownBy(() -> MarkdownFormatter.createHeader(7, "Invalid"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Header level must be between 1 and 6");
  }

  @Test
  @DisplayName("Should create centered block")
  void testCreateCenteredBlock() {
    String block = MarkdownFormatter.createCenteredBlock("Content");
    
    assertThat(block).contains("<div align=\"center\">");
    assertThat(block).contains("Content");
    assertThat(block).contains("</div>");
  }

  @Test
  @DisplayName("Should create code block with language")
  void testCreateCodeBlockWithLanguage() {
    String code = MarkdownFormatter.createCodeBlock("echo 'Hello'", "bash");
    
    assertThat(code).startsWith("```bash");
    assertThat(code).contains("echo 'Hello'");
    assertThat(code).endsWith("```\n\n");
  }

  @Test
  @DisplayName("Should create code block without language")
  void testCreateCodeBlockWithoutLanguage() {
    String code = MarkdownFormatter.createCodeBlock("code", null);
    assertThat(code).startsWith("```\n");
  }

  @Test
  @DisplayName("Should create unordered list from items")
  void testCreateUnorderedList() {
    List<String> items = List.of("Item 1", "Item 2", "Item 3");
    String list = MarkdownFormatter.createUnorderedList(items);
    
    assertThat(list).contains("- Item 1");
    assertThat(list).contains("- Item 2");
    assertThat(list).contains("- Item 3");
  }

  @Test
  @DisplayName("Should return empty string for null list")
  void testCreateUnorderedListWithNull() {
    String list = MarkdownFormatter.createUnorderedList(null);
    assertThat(list).isEmpty();
  }

  @Test
  @DisplayName("Should create unordered list from text")
  void testCreateUnorderedListFromText() {
    String text = "Feature 1\nFeature 2\nFeature 3";
    String list = MarkdownFormatter.createUnorderedListFromText(text);
    
    assertThat(list).contains("- Feature 1");
    assertThat(list).contains("- Feature 2");
    assertThat(list).contains("- Feature 3");
  }

  @Test
  @DisplayName("Should create link")
  void testCreateLink() {
    String link = MarkdownFormatter.createLink("GitHub", "https://github.com");
    assertThat(link).isEqualTo("[GitHub](https://github.com)");
  }

  @Test
  @DisplayName("Should create image")
  void testCreateImage() {
    String image = MarkdownFormatter.createImage("Alt Text", "image.png");
    assertThat(image).isEqualTo("![Alt Text](image.png)");
  }

  @Test
  @DisplayName("Should create horizontal rule")
  void testCreateHorizontalRule() {
    String rule = MarkdownFormatter.createHorizontalRule();
    assertThat(rule).isEqualTo("---\n\n");
  }

  @Test
  @DisplayName("Should create table of contents link")
  void testCreateTocLink() {
    String link = MarkdownFormatter.createTocLink("Installation");
    assertThat(link).isEqualTo("- [Installation](#installation)");
  }

  @Test
  @DisplayName("Should handle TOC link with special characters")
  void testCreateTocLinkSpecialChars() {
    String link = MarkdownFormatter.createTocLink("API Reference!");
    assertThat(link).contains("#api-reference");
  }

  @Test
  @DisplayName("Should create bold text")
  void testCreateBold() {
    String bold = MarkdownFormatter.createBold("Important");
    assertThat(bold).isEqualTo("**Important**");
  }

  @Test
  @DisplayName("Should create italic text")
  void testCreateItalic() {
    String italic = MarkdownFormatter.createItalic("Emphasis");
    assertThat(italic).isEqualTo("*Emphasis*");
  }

  @Test
  @DisplayName("Should create inline code")
  void testCreateInlineCode() {
    String code = MarkdownFormatter.createInlineCode("npm install");
    assertThat(code).isEqualTo("`npm install`");
  }

  @Test
  @DisplayName("Should sanitize special characters")
  void testSanitize() {
    String sanitized = MarkdownFormatter.sanitize("Test * _ [ ]");
    
    assertThat(sanitized).contains("\\*");
    assertThat(sanitized).contains("\\_");
    assertThat(sanitized).contains("\\*");
    assertThat(sanitized).contains("\\_");
  }

  @Test
  @DisplayName("Should return empty string when sanitizing null")
  void testSanitizeNull() {
    String sanitized = MarkdownFormatter.sanitize(null);
    assertThat(sanitized).isEmpty();
  }
}
