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

package de.paladinsinn.rollenspielcons.persistence;


import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;


/**
 * The embeddable representation of a displayable name with an optional URI.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Embeddable
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"name", "uri"})
public class PersistedDisplayableName implements HasDisplayText, Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Column(name = "NAME", length = 250, nullable = false)
  @NotBlank(message = "The name must not be blank.")
  private String name;
  
  @Column(name = "URI", length = 250)
  @Nullable
  private String uri;

  @JsonIgnore
  @Transient
  @Override
  public String getDisplayText() {
    return name;
  }
}
