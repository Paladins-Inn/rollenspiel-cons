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

package de.paladinsinn.rollenspielcons.domain.api.calendars;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * The port for the model to persist calendars.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
public interface CalendarPersistencePort {
  /**
   * @param calendar to create
   */
  Calendar create(@NotNull final Calendar calendar) throws CalendarPersistenceException;
  
  
  /**
   * @param id of the calendar to retrieve
   * @return The calendar with the given id or an empty Optional
   */
  Optional<Calendar> retrieveById(@NotNull final Long id);
  
  /**
   * @return all calendars
   */
  List<Calendar> retrieveAll();
  
  /**
   * @param externalId of the calendar to retrieve
   * @return The calendar with the given externalId or an empty Optional
   */
  List<Calendar> retrieveByOwner(@NotBlank final String owner);
  
  /**
   * @param type of the calendar to retrieve
   * @return The calendar with the given type or an empty Optional
   */
  List<Calendar> retrieveByType(@NotNull final CalendarType type);
  
  
  /**
   * @param calendar to update
   */
  void update(@NotNull final Calendar calendar) throws CalendarPersistenceException;
  
  /**
   * @param calendar to delete
   */
  void delete(@NotNull final Calendar calendar) throws CalendarPersistenceException;
  
  /**
   * @param id of the calendar to delete
   */
  void delete(@NotNull final Long id) throws CalendarPersistenceException;
}
