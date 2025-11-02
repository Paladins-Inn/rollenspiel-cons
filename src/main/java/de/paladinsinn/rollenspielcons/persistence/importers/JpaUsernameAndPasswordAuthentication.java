package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-19
 */
@Embeddable
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Slf4j
public final class JpaUsernameAndPasswordAuthentication
    extends AbstractJpaAuthenticationBase
    implements ImporterAuthentication {
  private static final int MAXIMUM_TOKEN_LENTH = 800;

  @Column(name = "USERNAME", length = 50)
  @ToString.Include
  private String username;
  
  @NotBlank(message = "The system does not work without refresh token.")
  @Size(max = MAXIMUM_TOKEN_LENTH, message = "The refresh token must not be longer than {max} bytes.")
  public void setPassword(@NotBlank final String password) {
    super.setPassword(password);
  }
  
  @Override
  public HttpHeaders authenticate(final HttpHeaders httpHeaders) {
    log.trace("enter -  {}", httpHeaders);
    
    httpHeaders.setBasicAuth(getUsername(), getPassword());

    log.trace("exit - {}", httpHeaders);
    return httpHeaders;
  }
}
