package de.paladinsinn.rollenspielcons.domain.model.importers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RefreshTokenAuthentication implements
                                        de.paladinsinn.rollenspielcons.domain.api.integrations.RefreshTokenAuthentication {
  private String refreshToken;
  
  
}
