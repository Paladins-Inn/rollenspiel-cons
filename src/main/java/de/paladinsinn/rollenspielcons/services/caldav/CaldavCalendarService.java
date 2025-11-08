package de.paladinsinn.rollenspielcons.services.caldav;

import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarImportException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarService;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.persistence.mapper.TemporalConverter;
import de.paladinsinn.rollenspielcons.services.geo.LocationService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


/**
 * A service to fetch events from a CalDAV calendar (e.g. Nextcloud).
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class CaldavCalendarService implements CalendarService {
  /** The default number of months to fetch events if no end date ist specified. */
  public static final int MONTHS_TO_ADD = 3;
  
  
  private final VEventMapper mapper;
  private final RestClient restClient;
  private final LocationService locationService;
  private final TemporalConverter temporalConverter;
  
  @Override
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar
  ) throws CalendarException {
    log.trace("enter -  {}", calendar);
    
    LocalDate start = calculateStartOfMonth();
    LocalDate end = start.plusMonths(MONTHS_TO_ADD);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, start, end);
    
    var result = mapEvents(vEvents, calendar.getOwner());

    log.trace("exit - {}", result);
    return result;
  }
  
  private LocalDate calculateStartOfMonth() {
    log.trace("enter - ");
    
    LocalDate now = LocalDate.now();
    var result = LocalDate.of(now.getYear(), now.getMonth(), 1);

    log.trace("exit - {}", result);
    return result;

  }
  
  @Override
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      @NotNull final LocalDate from
  ) throws CalendarException {
    log.trace("enter -  {}, {}", calendar, from);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, from, from.plusMonths(MONTHS_TO_ADD));

    var result = mapEvents(vEvents, calendar.getOwner());

    log.trace("exit - {}", result);
    return result;
  }
  
  @Override
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      @NotNull final LocalDate from,
      @NotNull final LocalDate till
  ) throws CalendarException {
    log.trace("enter -  {}, {}, {}", calendar, from, till);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, from, till);

    var result = mapEvents(vEvents, calendar.getOwner());

    log.trace("exit - {}", result);
    return result;
  }
  
  
  private List<VEvent> fetchEventsFromCalDav(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      @NotNull LocalDate from,
      LocalDate till
  ) throws CalendarException {
    log.trace("enter -  {}, {}, {}, {}", restClient, calendar, from, till);
    
    
    List<VEvent> events = new ArrayList<>();
    byte[] calendarBytes = null;
    if (restClient != null) {
      calendarBytes = restClient
          .get()
          .uri(calculateCalendarUri(calendar, from, till))
          .headers(calendar::authenticate)
          .retrieve()
          .body(byte[].class);
    } else {
      log.error("WebClient is not configured.");
    }
    
    
    if (calendarBytes == null) {
      return events;
    }
    
    events = readEvents(calendar, calendarBytes, from, till);
    
    log.trace("exit - {}", events.size());
    return events;
  }
  
  private String calculateCalendarUri(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      final LocalDate from,
      final LocalDate till
  ) {
    log.trace("enter -  {}, {}, {}", calendar, from, till);
    
    String result = calendar.getCalendarId() + "?export&expand=1";
    
    if (from != null) {
      result += "&start=" + from.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
    
    if (till != null) {
      result += "&end=" + till.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
    
    log.trace("exit - {}", result);
    return result;
  }
  
  private List<VEvent> readEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar cal,
      @NotNull final byte[] calendarBytes,
      @NotNull final LocalDate from,
      @NotNull final LocalDate till
  ) throws CalendarException {
    log.trace("enter -  {}, {}, {}", calendarBytes.length, from, till);
    
    try (InputStream in = new java.io.ByteArrayInputStream(calendarBytes)) {
      Calendar calendar = new CalendarBuilder().build(in);
      
      List<VEvent> events = calendar.getComponents().stream()
                       .map(c -> (VEvent) c)
                       .filter(e -> isBetween(e, from, till))
                       .toList();
      
      log.trace("Exit marker with: {}", events.size());
      return events;
    } catch (ParserException | IOException e) {
      log.warn("Could not read the CALDAV events from external service.", e);
      throw new CalendarImportException(cal, "Could not read the CALDAV events from external service.", e);
    }
  }
  
  private boolean isBetween(
      @NotNull final VEvent event,
      @NotNull final LocalDate from,
      @NotNull final LocalDate till
  ) {
    log.trace(
        "enter -  {}, {}, {}",
        event.getUid().isPresent() ? event.getUid().get().getValue() : "no-uid",
        from,
        till
    );
    
    if (event.getDateTimeStart().isEmpty()) {
      log.debug("Event {} has no start date.", event.getUid());
      var result = true;

      log.trace("exit - {}", result);
      return result;
    }
    
    LocalDate date = LocalDate.ofInstant(temporalConverter.convertToDatabaseColumn(event.getDateTimeStart().get().getDate()), ZoneId.systemDefault());
    
    var result = (
        (date.isEqual(from) || date.isAfter(from)) &&
        (date.isEqual(till) || date.isBefore(till))
    );
    
    log.trace("exit - {}", result);
    return result;
  }
  
  private List<Event> mapEvents(@NotNull List<VEvent> events, @NotBlank final String owner) {
    log.trace("enter -  {}, {}", events.size(), owner);
    
    var result = events.stream()
        .map(e -> mapper.toEvent(e, locationService))
        .map(e -> (Event)e.toBuilder().owner(owner).build())
        .toList();

    log.trace("exit - {}", result);
    return result;
  }
}