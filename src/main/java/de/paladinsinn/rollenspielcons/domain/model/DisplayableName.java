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

package de.paladinsinn.rollenspielcons.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.beans.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 * A name that can be displayed in a UI.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @__(@Deprecated))
@Getter
@ToString
public class DisplayableName implements HasDisplayText {
  /**
   * The name of the event.
   */
  @Schema(
      description = "The name of the event.",
      examples = { "PhysicalEvent Name", "Another PhysicalEvent" },
      minLength = 3,
      maxLength = 250,
      required = true
  )
  @NotNull(message = "The name must be present.")
  @Size(min = 3, max = 250, message = "The name must be between {min} and {max} characters long.")
  private String name;
  
  /**
   * An URI for the object this name belongs to.
   */
  @Schema(
      description = "An URI for the object this name belongs to.",
      examples = { "https://rollenspiel-cons.info", "https://www.paladins-inn.de" },
      minLength = 10,
      maxLength = 250,
      required = true
  )
  @NotNull(message = "The URI must be present.")
  @Size(min = 3, max = 100, message = "The name must be between {min} and {max} characters long.")
  @ToString.Exclude
  private String uri;
  
  /**
   * Returns the display text for this object.
   *
   * @return the display text
   */
  @Override
  @JsonIgnore
  @Transient
  public String getDisplayText() {
    return name;
  }
}
