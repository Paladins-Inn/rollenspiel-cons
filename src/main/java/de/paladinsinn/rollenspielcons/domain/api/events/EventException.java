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

package de.paladinsinn.rollenspielcons.domain.api.events;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception concerning a event.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class EventException extends Exception {
  private final Event event;
  
  public EventException(@NotNull final Event event, final String message) {
    super(message);
    
    this.event = event;
  }

  public EventException(@NotNull final Event event, final String message, final Throwable cause) {
    super(message, cause);
    
    this.event = event;
  }

  public EventException(@NotNull final Event event, final Throwable cause) {
    super(cause.getMessage(), cause);
    
    this.event = event;
  }
}
