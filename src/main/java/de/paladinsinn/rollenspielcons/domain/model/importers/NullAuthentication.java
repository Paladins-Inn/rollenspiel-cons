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

package de.paladinsinn.rollenspielcons.domain.model.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;


/**
 * An empty authentication object.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@ToString
@Slf4j
public class NullAuthentication implements ImporterAuthentication {
  @Override
  public HttpHeaders authenticate(final HttpHeaders httpHeaders) {
    log.trace("enter -  {}", httpHeaders);

    var result = httpHeaders;

    log.trace("exit - {}", result);
    return result;
  }
}
