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

package de.paladinsinn.rollenspielcons.domain.model.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Schema(
    title = "WebLocation",
    description = "A web locations with a display name and a URI.",
    examples = {
        """
        {
          "displayText": "Paladins Inn",
          "uri": "https://www.paladins-inn.de"
        }
        """,
        """
        {
          "displayText": "Discord Server",
          "uri": "https://discord.gg/your-invite-code"
        }
        """
    })
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WebLocationImpl extends LocationImpl implements WebLocation {
  @Schema(
      title = "Name of the website",
      description = "The display name of the website.",
      examples = {"Paladins Inn"}
  )
  @NotBlank(message = "The display text must be set.")
  @Size(min = 1, max = 250, message = "The name must be between {min} and {max} characters long.")
  private String displayText;
  
  @Schema(
      title = "The URI of the website",
      description = "The URI (link) to the website.",
      examples = {"https://discord.gg/your-invite-code"}
  )
  @NotBlank(message = "The URI must be set.")
  @Size(min = 10, max = 250, message = "The URI must be between {min} and {max} characters long.")
  private String uri;
}
