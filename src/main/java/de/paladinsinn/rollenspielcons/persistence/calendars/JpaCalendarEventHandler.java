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

package de.paladinsinn.rollenspielcons.persistence.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarPersistenceException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.events.CreatePersistedCalendarEvent;
import de.paladinsinn.rollenspielcons.domain.api.calendars.events.DeletePersistetedCalendarEvent;
import de.paladinsinn.rollenspielcons.domain.api.calendars.events.UpdatePersistedCalendarEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


/**
 * Handles event bus events for calendar persistence.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JpaCalendarEventHandler {
  private final JpaCalendarPersistencePort port;
  
  @EventListener
  public void createCalendar(CreatePersistedCalendarEvent event)
      throws CalendarPersistenceException {
    log.trace("enter -  {}", event);
    
    port.create(event.getCalendar());
    
    log.trace("Exiting method");
  }
  
  
  @EventListener
  public void updateCalendar(UpdatePersistedCalendarEvent event)
      throws CalendarPersistenceException {
    log.trace("enter -  {}", event);
    
    port.update(event.getCalendar());
  }
  
  
  @EventListener
  public void deleteCalendar(DeletePersistetedCalendarEvent event)
      throws CalendarPersistenceException {
    log.trace("enter -  {}", event);
    
    port.delete(event.getCalendar());
    
    log.trace("Exiting method");
  }
}
