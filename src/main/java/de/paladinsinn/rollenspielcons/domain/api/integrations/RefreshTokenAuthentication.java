package de.paladinsinn.rollenspielcons.domain.api.integrations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpHeaders;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@JsonDeserialize(as = de.paladinsinn.rollenspielcons.domain.model.importers.RefreshTokenAuthentication.class)
public interface RefreshTokenAuthentication extends ImporterAuthentication {
  default HttpHeaders authenticate(HttpHeaders headers) {
    throw new UnsupportedOperationException("Not supported for refresh-token authentication.");
  }
  
  /**
   * @return the refresh token for the google-auth-library.
   */
  @NotBlank(message = "The refresh token must not be blank.")
  @Size(min = 1, max = 800, message = "The refresh token must be between {min} and {max} characters long.")
  String getRefreshToken();
}
