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
