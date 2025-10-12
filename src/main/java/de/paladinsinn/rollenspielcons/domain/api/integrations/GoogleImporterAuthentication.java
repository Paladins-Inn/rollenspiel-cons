package de.paladinsinn.rollenspielcons.domain.api.integrations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.model.importers.GoogleImporterAuthenticationImpl;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@JsonDeserialize(as = GoogleImporterAuthenticationImpl.class)
public interface GoogleImporterAuthentication extends ImporterAuthentication {
  /**
   * @return the refresh token for the google-auth-library.
   */
  String getRefreshToken();
}
