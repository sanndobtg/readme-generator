package com.readme.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for formatting Markdown content.
 * Provides methods to create properly formatted Markdown sections and elements.
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
public final class MarkdownFormatter {

  private static final String NEWLINE = "\n";
  private static final String DOUBLE_NEWLINE = "\n\n";

  private MarkdownFormatter() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Creates a Markdown header of the specified level.
   *
   * @param level the header level (1-6)
   * @param text the header text
   * @return the formatted header
   */
  public static String createHeader(int level, String text) {
    if (level < 1 || level > 6) {
      throw new IllegalArgumentException("Header level must be between 1 and 6");
    }
    return "#".repeat(level) + " " + text + DOUBLE_NEWLINE;
  }

  /**
   * Creates a centered div block with content.
   *
   * @param content the content to center
   * @return the formatted centered block
   */
  public static String createCenteredBlock(String content) {
    return "<div align=\"center\">" + DOUBLE_NEWLINE 
        + content + DOUBLE_NEWLINE 
        + "</div>" + DOUBLE_NEWLINE;
  }

  /**
   * Creates a code block with optional language specification.
   *
   * @param code the code content
   * @param language the programming language (optional)
   * @return the formatted code block
   */
  public static String createCodeBlock(String code, String language) {
    String lang = StringUtils.isNotBlank(language) ? language : "";
    return "```" + lang + NEWLINE + code + NEWLINE + "```" + DOUBLE_NEWLINE;
  }

  /**
   * Creates an unordered list from a collection of items.
   *
   * @param items the list items
   * @return the formatted list
   */
  public static String createUnorderedList(List<String> items) {
    if (items == null || items.isEmpty()) {
      return "";
    }
    
    return items.stream()
        .filter(StringUtils::isNotBlank)
        .map(item -> "- " + item)
        .collect(Collectors.joining(NEWLINE)) + DOUBLE_NEWLINE;
  }

  /**
   * Creates an unordered list from a multi-line string.
   *
   * @param text the multi-line text (one item per line)
   * @return the formatted list
   */
  public static String createUnorderedListFromText(String text) {
    if (StringUtils.isBlank(text)) {
      return "";
    }
    
    List<String> items = Arrays.asList(text.split("\\r?\\n"));
    return createUnorderedList(items);
  }

  /**
   * Creates a Markdown link.
   *
   * @param text the link text
   * @param url the URL
   * @return the formatted link
   */
  public static String createLink(String text, String url) {
    return "[" + text + "](" + url + ")";
  }

  /**
   * Creates a Markdown image.
   *
   * @param altText the alternative text
   * @param url the image URL
   * @return the formatted image
   */
  public static String createImage(String altText, String url) {
    return "![" + altText + "](" + url + ")";
  }

  /**
   * Creates a horizontal rule.
   *
   * @return the formatted horizontal rule
   */
  public static String createHorizontalRule() {
    return "---" + DOUBLE_NEWLINE;
  }

  /**
   * Creates a table of contents link.
   *
   * @param text the section name
   * @return the formatted TOC link
   */
  public static String createTocLink(String text) {
    String anchor = text.toLowerCase()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("\\s+", "-");
    return "- [" + text + "](#" + anchor + ")";
  }

  /**
   * Creates bold text.
   *
   * @param text the text to make bold
   * @return the formatted bold text
   */
  public static String createBold(String text) {
    return "**" + text + "**";
  }

  /**
   * Creates italic text.
   *
   * @param text the text to make italic
   * @return the formatted italic text
   */
  public static String createItalic(String text) {
    return "*" + text + "*";
  }

  /**
   * Creates inline code.
   *
   * @param code the code text
   * @return the formatted inline code
   */
  public static String createInlineCode(String code) {
    return "`" + code + "`";
  }

  /**
   * Sanitizes text for safe inclusion in Markdown.
   * Escapes special Markdown characters.
   *
   * @param text the text to sanitize
   * @return the sanitized text
   */
  public static String sanitize(String text) {
    if (StringUtils.isBlank(text)) {
      return "";
    }
    
    return text.replace("\\", "\\\\")
               .replace("`", "\\`")
               .replace("*", "\\*")
               .replace("_", "\\_")
               .replace("[", "\\[")
               .replace("]", "\\]");
  }
}
