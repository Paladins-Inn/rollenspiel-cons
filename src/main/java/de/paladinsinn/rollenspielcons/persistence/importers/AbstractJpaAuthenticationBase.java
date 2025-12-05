/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
