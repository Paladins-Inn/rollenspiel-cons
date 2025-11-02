package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.persistence.mapper.AesGcmStringCryptoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
public abstract class AbstractJpaAuthenticationBase {
  private static final int MAXIMUM_TOKEN_LENTH = 800;
  
  @Getter
  @Setter(AccessLevel.PROTECTED)
  @Column(name = "PASSWORD", length = MAXIMUM_TOKEN_LENTH)
  @Convert(converter = AesGcmStringCryptoConverter.class)
  @NotBlank(message = "The system does not work without token.")
  @Size(max = MAXIMUM_TOKEN_LENTH, message = "The token must not be longer than {max} bytes.")
  private String password;
}
