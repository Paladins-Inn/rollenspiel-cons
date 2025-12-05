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
