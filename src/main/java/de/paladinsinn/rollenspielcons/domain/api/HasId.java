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

package de.paladinsinn.rollenspielcons.domain.api;


import jakarta.validation.constraints.Min;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
public interface HasId {
  /**
   * This is no database sequence but a <a href="https://en.wikipedia.org/wiki/Snowflake_ID">Snowflake ID</a>.
   *
   * <p>We normally use the generator provided by {@link de.paladinsinn.rollenspielcons.config.SnowflakeIdGeneratorConfig}.</p>
   */
  @Schema(
      description = "Unique identifier of the event.",
      comment = "This id has to be generated when the event is created. It is a <a href=\"https://en.wikipedia.org/wiki/Snowflake_ID\">Snowflake ID</a>.",
      examples = { "123456789012345", "987654321098765432" },
      minimum = "1",
      required = true
  )
  @Min(value = 1, message = "The id must be greater than {value}. Please use a Snowflake ID.")
  long getId();
}
