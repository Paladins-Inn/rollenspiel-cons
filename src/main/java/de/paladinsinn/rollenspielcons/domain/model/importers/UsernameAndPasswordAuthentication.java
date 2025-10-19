package de.paladinsinn.rollenspielcons.domain.model.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpHeaders;


/**
 * An empty authentication object.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XSlf4j
public class UsernameAndPasswordAuthentication implements ImporterAuthentication {
  @Getter
  @NotBlank(message = "The username must not be blank.")
  private String username;
  @NotBlank(message = "The password must not be blank.")
  private String password;
  
  @Override
  public HttpHeaders authenticate(@NotNull final HttpHeaders httpHeaders) {
    log.entry(httpHeaders, username, "<redacted>");
    
    httpHeaders.setBasicAuth(username, password);
    
    return log.exit(httpHeaders);
  }
  
}
