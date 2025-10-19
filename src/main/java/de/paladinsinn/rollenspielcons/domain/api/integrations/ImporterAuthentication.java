package de.paladinsinn.rollenspielcons.domain.api.integrations;


import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;

/**
 * Authentication for calendar syncs.
 *
 * <p>Since these schemes are heavily calendar service specific, there is no method in here.
 * It is only a marker interface where you have to check the implementation yourself.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
public interface ImporterAuthentication {
  /**
   * Adds authentication to the given client.
   *
   * @param httpHeaders The http headers to add authentication to.
   * @return the http headers with authentication.
   * @throws UnsupportedOperationException if the authentication is not supported by the importer.
   */
  HttpHeaders authenticate(@NotNull final HttpHeaders httpHeaders);
}
