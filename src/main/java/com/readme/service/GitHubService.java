package com.readme.service;

import com.readme.exception.GitHubExportException;
import com.readme.util.BadgeGenerator;
import java.io.IOException;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for interacting with the GitHub API.
 * Handles authentication, repository access, and README file operations.
 *
 * <p>This service supports:
 * <ul>
 *   <li>Creating new README.md files in repositories</li>
 *   <li>Updating existing README.md files</li>
 *   <li>Token validation</li>
 *   <li>Repository access verification</li>
 * </ul>
 *
 * @author README Generator Team
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class GitHubService {

  private static final Logger logger = LoggerFactory.getLogger(GitHubService.class);
  private static final String README_FILENAME = "README.md";
  private static final String COMMIT_MESSAGE_CREATE = "Create README.md via README Generator";
  private static final String COMMIT_MESSAGE_UPDATE = "Update README.md via README Generator";

  @Value("${github.token:}")
  private String defaultGitHubToken;

  /**
   * Exports README content to a GitHub repository.
   * Creates a new README.md file or updates an existing one.
   *
   * @param repositoryUrl the full GitHub repository URL
   * @param readmeContent the README markdown content to export
   * @param token the GitHub personal access token
   * @return a success message describing the operation performed
   * @throws GitHubExportException if export fails
   */
  public String exportToGitHub(String repositoryUrl, String readmeContent, String token)
      throws GitHubExportException {
    try {
      logger.info("Attempting to export README to repository: {}", repositoryUrl);

      String authToken = determineToken(token);
      validateToken(authToken);

      String repoPath = BadgeGenerator.extractRepoPath(repositoryUrl);
      validateRepositoryPath(repoPath);

      GitHub github = connectToGitHub(authToken);
      GHRepository repository = getRepository(github, repoPath);

      boolean isUpdate = updateOrCreateReadme(repository, readmeContent);

      String message = String.format("README.md %s successfully in %s",
          isUpdate ? "updated" : "created", repoPath);
      
      logger.info(message);
      return message;

    } catch (IOException e) {
      logger.error("Failed to export README to GitHub: {}", repositoryUrl, e);
      throw new GitHubExportException("Failed to export to GitHub: " + e.getMessage(), e);
    }
  }

  /**
   * Validates a GitHub personal access token.
   *
   * @param token the token to validate
   * @return true if the token is valid
   * @throws GitHubExportException if validation fails
   */
  public boolean validateToken(String token) throws GitHubExportException {
    try {
      if (token == null || token.isBlank()) {
        throw new GitHubExportException("GitHub token is required");
      }

      GitHub github = new GitHubBuilder().withOAuthToken(token).build();
      github.checkApiUrlValidity();
      boolean isValid = github.isCredentialValid();

      if (!isValid) {
        throw new GitHubExportException("Invalid GitHub token");
      }

      logger.info("GitHub token validated successfully");
      return true;

    } catch (IOException e) {
      logger.error("Token validation failed", e);
      throw new GitHubExportException("Token validation failed: " + e.getMessage(), e);
    }
  }

  /**
   * Determines which token to use (provided or default).
   *
   * @param providedToken the token provided by the user
   * @return the token to use for authentication
   */
  private String determineToken(String providedToken) {
    return (providedToken != null && !providedToken.isBlank()) 
        ? providedToken 
        : defaultGitHubToken;
  }

  /**
   * Validates the repository path format.
   *
   * @param repoPath the repository path to validate
   * @throws GitHubExportException if the path is invalid
   */
  private void validateRepositoryPath(String repoPath) throws GitHubExportException {
    if (repoPath == null || repoPath.isBlank()) {
      throw new GitHubExportException("Invalid repository URL");
    }

    String[] parts = repoPath.split("/");
    if (parts.length != 2) {
      throw new GitHubExportException(
          "Invalid repository URL format. Expected: github.com/owner/repo");
    }
  }

  /**
   * Connects to GitHub using the provided token.
   *
   * @param token the authentication token
   * @return authenticated GitHub instance
   * @throws IOException if connection fails
   */
  private GitHub connectToGitHub(String token) throws IOException {
    logger.debug("Connecting to GitHub API");
    return new GitHubBuilder().withOAuthToken(token).build();
  }

  /**
   * Gets a repository by its path.
   *
   * @param github the authenticated GitHub instance
   * @param repoPath the repository path (owner/repo)
   * @return the repository object
   * @throws IOException if the repository cannot be accessed
   */
  private GHRepository getRepository(GitHub github, String repoPath) throws IOException {
    logger.debug("Accessing repository: {}", repoPath);
    
    try {
      return github.getRepository(repoPath);
    } catch (IOException e) {
      throw new IOException(
          "Unable to access repository. Check that the repository exists "
          + "and your token has the required permissions.", e);
    }
  }

  /**
   * Updates an existing README or creates a new one.
   *
   * @param repository the target repository
   * @param content the README content
   * @return true if updating existing file, false if creating new file
   * @throws IOException if the operation fails
   */
  private boolean updateOrCreateReadme(GHRepository repository, String content)
      throws IOException {
    GHContent existingReadme = getExistingReadme(repository);

    if (existingReadme != null) {
      logger.info("Updating existing README.md");
      existingReadme.update(content, COMMIT_MESSAGE_UPDATE);
      return true;
    } else {
      logger.info("Creating new README.md");
      repository.createContent()
          .content(content)
          .path(README_FILENAME)
          .message(COMMIT_MESSAGE_CREATE)
          .commit();
      return false;
    }
  }

  /**
   * Attempts to retrieve an existing README file from the repository.
   *
   * @param repository the repository to check
   * @return the README file content object, or null if not found
   */
  private GHContent getExistingReadme(GHRepository repository) {
    try {
      return repository.getFileContent(README_FILENAME);
    } catch (IOException e) {
      logger.debug("No existing README.md found in repository");
      return null;
    }
  }
}
