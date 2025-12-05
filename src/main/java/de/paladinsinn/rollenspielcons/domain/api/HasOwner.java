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


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
public interface HasOwner {
  /**
   * The owner of this object.
   *
   * <p>This is normally the sub of the identity.</p>
   *
   * @return the owner of this object
   */
  @Schema(
      description = "The owner of this object.",
      comment = "This is normally the sub of the identity.",
      examples = {
          "123451234512345"
      },
      minLength = 2,
      maxLength = 100,
      required = true
  )
  @NotBlank(message = "The owner has to be set.")
  @Size(min = 2, max = 100, message = "The owner must be between {min} and {max} characters long.")
  String getOwner();
}
