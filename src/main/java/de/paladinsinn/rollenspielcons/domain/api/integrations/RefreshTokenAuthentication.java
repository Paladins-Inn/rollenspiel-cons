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

package de.paladinsinn.rollenspielcons.domain.api.integrations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpHeaders;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@JsonDeserialize(as = de.paladinsinn.rollenspielcons.domain.model.importers.RefreshTokenAuthentication.class)
public interface RefreshTokenAuthentication extends ImporterAuthentication {
  default HttpHeaders authenticate(HttpHeaders headers) {
    throw new UnsupportedOperationException("Not supported for refresh-token authentication.");
  }
  
  /**
   * @return the refresh token for the google-auth-library.
   */
  @NotBlank(message = "The refresh token must not be blank.")
  @Size(min = 1, max = 800, message = "The refresh token must be between {min} and {max} characters long.")
  String getRefreshToken();
}
