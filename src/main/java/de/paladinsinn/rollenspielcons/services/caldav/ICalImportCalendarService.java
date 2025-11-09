package de.paladinsinn.rollenspielcons.services.caldav;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarImportException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarPersistenceException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.ImportCalendarService;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.model.calendars.ICalCalendar;
import de.paladinsinn.rollenspielcons.persistence.calendars.JpaCalendarPersistencePort;
import de.paladinsinn.rollenspielcons.persistence.events.EventMapper;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEvent;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEventRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * Retrieves data from an ICal Calendar and persists the events to the database.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ICalImportCalendarService implements ImportCalendarService {
  private final ICalCalendarService client;
  private final JpaCalendarPersistencePort calendarPort;
  
  private final JpaEventRepository eventRepository;
  private final EventMapper eventMapper;
  
  
  @Override
  public void importCalendar(@NotNull final Calendar calendar) throws CalendarException {
    log.trace("enter -  {}", calendar);
    
    var cal = loadCalenderFromPersistence(calendar);
    
    List<Event> events = retrieveEvents(calendar, (ICalCalendar) cal);
    
    persistLoadedEvents(events, (ICalCalendar) cal);
  }
  
  private @NotNull Calendar loadCalenderFromPersistence(@NotNull final Calendar calendar)
      throws CalendarImportException {
    
    if (! (calendar instanceof ICalCalendar)) {
      log.warn("Invalid calendar type for this ical import service. calendar={}", calendar);
      throw new CalendarImportException(calendar, "Cannot import non-ICAL calendar (type=" + calendar.getSyncType() + ").");
    }
    
    return calendarPort
        .retrieveById(calendar.getId())
        .orElseThrow(
          () -> new CalendarImportException(calendar, "Cannot retrieve calendar with id " + calendar.getId())
        );
  }
  
  private List<Event> retrieveEvents(
      @NotNull final Calendar calendar,
      @NotNull final ICalCalendar cal
  ) throws CalendarException {
    List<Event> events;
    try {
      events = client.getEvents(calendar);
    } catch (CalendarException e) {
      importFailing(e, cal);
      throw e; // to make java IDE happy. Will never be reached ...
    }
    return events;
  }
  
  private void importFailing(
      @NotNull final CalendarException e,
      @NotNull final ICalCalendar cal
  ) throws CalendarException {
    var updatedCalendar = cal.toBuilder()
                             .lastSyncTime(OffsetDateTime.now())
                             .lastSyncAttemptTime(OffsetDateTime.now())
                             .lastSyncResult(SyncSuccess.CONVERSION_FAILED)
                             .build();
    calendarPort.update(updatedCalendar);
    
    log.warn("Could not retrieve events from calendar. calendar={}", updatedCalendar, e);
    throw e;
  }
  
  private void persistLoadedEvents(
      @NotNull final List<Event> events,
      @NotNull final ICalCalendar cal
  ) throws CalendarException {
    try {
      var saved = eventRepository.saveAll(
          events.stream()
                .map(eventMapper::toJpaEvent)
                .toList()
      );
      
      importWasOk(cal, events, saved);
    } catch (IllegalArgumentException | OptimisticLockException e) {
      importFailing(new CalendarImportException(cal, e.getMessage()), cal);
    }
  }
  
  private void importWasOk(
      @NotNull final ICalCalendar cal,
      @NotNull final List<Event> events,
      @NotNull final List<JpaEvent> saved
  ) throws CalendarPersistenceException {
    var updatedCalendar = cal.toBuilder()
                             .lastSyncTime(OffsetDateTime.now())
                             .lastSyncAttemptTime(OffsetDateTime.now())
                             .lastSyncResult(SyncSuccess.OK)
                             .build();
    calendarPort.update(updatedCalendar);
    
    log.info("Imported events. calendar={}, retrieved={},  saved={}", updatedCalendar, events.size(), saved.size());
  }
}
