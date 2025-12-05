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
