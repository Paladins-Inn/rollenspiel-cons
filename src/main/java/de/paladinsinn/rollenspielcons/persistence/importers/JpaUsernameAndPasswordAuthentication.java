package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.importers.UsernameAndPasswordAuthentication;
import de.paladinsinn.rollenspielcons.persistence.mapper.AesGcmStringCryptoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpHeaders;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-19
 */
@Embeddable
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString(onlyExplicitlyIncluded = true)
@XSlf4j
public final class JpaUsernameAndPasswordAuthentication extends UsernameAndPasswordAuthentication
    implements ImporterAuthentication {
  private static final int MAXIMUM_TOKEN_LENTH = 800;

  @Column(name = "USERNAME", length = 50)
  @ToString.Include
  private String username;
  
  @Column(name = "PASSWORD", length = MAXIMUM_TOKEN_LENTH)
  @Convert(converter = AesGcmStringCryptoConverter.class)
  @NotBlank(message = "The system does not work without refresh token.")
  @Size(max = MAXIMUM_TOKEN_LENTH, message = "The refresh token must not be longer than {max} bytes.")
  private String password;
  
  @Override
  public HttpHeaders authenticate(final HttpHeaders httpHeaders) {
    log.entry(httpHeaders);
    
    super.setUsername(username);
    super.setPassword(password);
    
    return log.exit(super.authenticate(httpHeaders));
  }
}
