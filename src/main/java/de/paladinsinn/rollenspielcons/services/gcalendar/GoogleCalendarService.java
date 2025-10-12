package de.paladinsinn.rollenspielcons.services.gcalendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.api.integrations.RefreshTokenAuthentication;
import de.paladinsinn.rollenspielcons.services.api.CalendarException;
import de.paladinsinn.rollenspielcons.services.api.CalendarService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@XSlf4j
public class GoogleCalendarService implements CalendarService {
  private final GoogleEventMapper mapper;
  private final GoogleCalenderServiceCreator serviceCreator;
  
  
  @Cacheable("googleCalendarEvents")
  @Counted(
      value = "google_calendar_requests_count", description = "Total calls to google calendar api"
  )
  @Timed(
      value = "google_calendar_requests_duration",
      description = "Time taken to call google calendar api", percentiles = {0.5, 0.95, 0.99}
  )
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar gcal
  ) throws CalendarException, IllegalArgumentException {
    log.entry(gcal);
    
    serviceCreator.verifyGoogleCalendar(gcal);
    
    try {
      Calendar service = serviceCreator.createService(
          ((RefreshTokenAuthentication) gcal.getAuthentication()).getRefreshToken()
      );
      
      Events events = loadEventsFromGoogle(service, gcal.getCalendarId());
      return log.exit(convertGoogleEvents(events));
    } catch (GeneralSecurityException | IOException e) {
      throw new CalendarException("Could not access Google Calendar", e);
    }
  }
  
  @Cacheable("googleCalendarEventsWithStart")
  @Counted(
      value = "google_calendar_start_requests_count",
      description = "Total calls to google calendar api"
  )
  @Timed(
      value = "google_calendar_start_requests_duration",
      description = "Time taken to call google calendar api", percentiles = {0.5, 0.95, 0.99}
  )
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar gcal,
      final LocalDate start
  ) throws CalendarException, IllegalArgumentException {
    log.entry(gcal, start);
    
    serviceCreator.verifyGoogleCalendar(gcal);
    
    try {
      Calendar service = serviceCreator.createService(
          ((RefreshTokenAuthentication) gcal.getAuthentication()).getRefreshToken()
      );
      
      Events events = loadEventsFromGoogle(service, gcal.getCalendarId(), start);
      return log.exit(convertGoogleEvents(events));
    } catch (GeneralSecurityException | IOException e) {
      throw new CalendarException("Could not access Google Calendar", e);
    }
  }
  
  @Cacheable("googleCalendarEventsWithStartAndEnd")
  @Counted(
      value = "google_calendar_start_end_requests_count",
      description = "Total calls to google calendar api"
  )
  @Timed(
      value = "google_calendar_start_end_requests_duration",
      description = "Time taken to call google calendar api", percentiles = {0.5, 0.95, 0.99}
  )
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar gcal,
      final LocalDate start,
      final LocalDate end
  ) throws CalendarException, IllegalArgumentException {
    log.entry(gcal, start, end);
    
    serviceCreator.verifyGoogleCalendar(gcal);
    
    try {
      Calendar service = serviceCreator.createService(
          ((RefreshTokenAuthentication) gcal.getAuthentication()).getRefreshToken()
      );
      
      Events events = loadEventsFromGoogle(service, gcal.getCalendarId(), start, end);
      return log.exit(convertGoogleEvents(events));
    } catch (GeneralSecurityException | IOException e) {
      throw new CalendarException("Could not access Google Calendar", e);
    }
  }
  
  
  private Events loadEventsFromGoogle(@NotNull Calendar service, @NotBlank final String calendarId)
      throws CalendarException {
    log.entry(service, calendarId);
    
    try {
      Events result = service.events().list(calendarId)
                             .setOrderBy("startTime")
                             .setSingleEvents(true)
                             .execute()
          ;
      
      return log.exit(result);
    } catch (IOException e) {
      throw log.throwing(new CalendarException("The google calendar could not be read.", e));
    }
  }
  
  private Events loadEventsFromGoogle(
      @NotNull Calendar service, @NotBlank final String calendarId, final LocalDate start)
      throws CalendarException {
    log.entry(service, calendarId, start);
    
    try {
      DateTime startDateTime = new DateTime(
          start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
      
      Events result = service.events().list(calendarId)
                             .setTimeMin(startDateTime)
                             .setOrderBy("startTime")
                             .setSingleEvents(true)
                             .execute()
          ;
      
      return log.exit(result);
    } catch (IOException e) {
      throw log.throwing(new CalendarException("The google calendar could not be read.", e));
    }
  }
  
  private Events loadEventsFromGoogle(
      @NotNull Calendar service, @NotBlank final String calendarId, final LocalDate start,
      final LocalDate end
  )
      throws CalendarException {
    log.entry(service, calendarId, start, end);
    
    try {
      DateTime startDateTime = new DateTime(
          start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
      
      // Add one day to include the given end date as well.
      DateTime endDateTime   = new DateTime(
          end.plusDays(1L).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
      );
      
      Events result = service.events().list(calendarId)
                             .setTimeMin(startDateTime)
                             .setTimeMax(endDateTime)
                             .setOrderBy("startTime")
                             .setSingleEvents(true)
                             .execute()
          ;
      
      return log.exit(result);
    } catch (IOException e) {
      throw log.throwing(new CalendarException("The google calendar could not be read.", e));
    }
  }
  
  private List<Event> convertGoogleEvents(final Events events) {
    log.entry(events);
    
    List<Event> result = events
        .getItems().stream()
        .map(this::convertGoogleEvent)
        .collect(Collectors.toList())
        ;
    
    return log.exit(result);
  }
  
  private Event convertGoogleEvent(com.google.api.services.calendar.model.Event event) {
    log.entry(event);
    
    return log.exit(mapper.toDomainEvent(event));
  }
}