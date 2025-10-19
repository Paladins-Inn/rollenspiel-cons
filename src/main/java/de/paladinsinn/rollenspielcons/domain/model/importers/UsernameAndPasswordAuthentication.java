package de.paladinsinn.rollenspielcons.domain.model.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@XSlf4j
public class UsernameAndPasswordAuthentication implements ImporterAuthentication {
  @Getter
  @Setter
  @ToString.Include
  private String username;
  
  @Setter
  private String password;
  
  @Override
  public HttpHeaders authenticate(@NotNull HttpHeaders httpHeaders) {
    log.entry(httpHeaders, username, "<redacted>");
    
    if (
        username != null && password != null
        && !username.isBlank() && !password.isBlank()
    ) {
      httpHeaders.setBasicAuth(username, password);
    }
    
    return log.exit(httpHeaders);
  }
  
}
