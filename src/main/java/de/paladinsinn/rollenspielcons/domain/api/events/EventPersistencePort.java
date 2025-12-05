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


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * The port for the model to persist events.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
public interface EventPersistencePort {
  /**
   * @param event to create
   * @return The created event with id and version set
   */
  Event create(@NotNull final Event event) throws EventPersistenceException;
  
  
  /**
   * @param id of the event to retrieve
   * @return The event with the given id or an empty Optional
   */
  Optional<Event> retrieveById(@NotNull final Long id);
  /**
   * @param owner of the event to retrieve
   * @return The event with the given owner or an empty Optional
   */
  List<Event> retrieveByOwner(@NotBlank final String owner);
  /**
   * @param label of the event to retrieve
   * @return The event with the given label or an empty Optional
   */
  List<Event> retrieveByLabel(@NotBlank final String label);
  /**
   * @param date of the event to retrieve
   * @return The event with the given calendar or an empty Optional
   */
  List<Event> retrieveByDate(@NotNull final LocalDate date);
  /**
   * @param start of the event to retrieve
   * @param end of the event to retrieve
   * @return The event between the given dates
   */
  List<Event> retrieveByDate(@NotNull final LocalDate start, @NotNull final LocalDate end);
  
  
  /**
   * @param event to update
   * @return The updated event
   */
  Event update(@NotNull final Event event) throws EventPersistenceException;
  
  /**
   * @param event to delete
   */
  void delete(@NotNull final Event event) throws EventPersistenceException;
}
