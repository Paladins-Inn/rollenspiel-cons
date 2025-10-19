package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.RefreshTokenAuthentication;
import de.paladinsinn.rollenspielcons.persistence.mapper.AesGcmStringCryptoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Embeddable
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(onlyExplicitlyIncluded = true)
public class JpaRefreshTokenAuthentication implements RefreshTokenAuthentication {
  private static final int MAXIMUM_TOKEN_LENTH = 800;
  
  @Column(name = "REFRESH_TOKEN", length = MAXIMUM_TOKEN_LENTH, nullable = false)
  @Convert(converter = AesGcmStringCryptoConverter.class)
  @NotBlank(message = "The system does not work without refresh token.")
  @Size(max = MAXIMUM_TOKEN_LENTH, message = "The refresh token must not be longer than {max} bytes.")
  private String refreshToken;
}
