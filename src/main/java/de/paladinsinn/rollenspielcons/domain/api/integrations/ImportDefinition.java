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


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import static de.paladinsinn.rollenspielcons.domain.api.integrations.ImportDefinition.ImportType.NONE;

/**
 * The definition of an import.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ImportDefinition {
  /**
   * The empty definition.
   *
   * <p>This means, the entity is not imported at all.</p>
   */
  public static final ImportDefinition EMPTY = ImportDefinition
      .builder()
      .name("none")
      .type(NONE)
      .build();

  @NotBlank(message = "Name must not be blank")
  private String name;
  
  @NotNull(message = "Type must be set")
  private ImportType type;
  
  public enum ImportType {
    NONE,
    CALENDAR,
    SPEAKER,
    PARTICIPANT;
  }
}
