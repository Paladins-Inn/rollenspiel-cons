package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.RefreshTokenAuthentication;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Embeddable
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class JpaRefreshTokenAuthentication
    extends AbstractJpaAuthenticationBase
    implements RefreshTokenAuthentication {
  @Override
  public String getRefreshToken() {
    return getPassword();
  }
}
