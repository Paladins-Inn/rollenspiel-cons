package de.paladinsinn.rollenspielcons.services.caldav;

import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.model.events.EventImpl;
import de.paladinsinn.rollenspielcons.persistence.mapper.TemporalConverter;
import de.paladinsinn.rollenspielcons.services.api.CalendarException;
import de.paladinsinn.rollenspielcons.services.api.CalendarService;
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
import lombok.extern.slf4j.XSlf4j;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * A service to fetch events from a CalDAV calendar (e.g. Nextcloud).
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@XSlf4j
public class CaldavCalendarService implements CalendarService {
  /** The default number of months to fetch events if no end date ist specified. */
  public static final int MONTHS_TO_ADD = 3;
  
  
  private final VEventMapper mapper;
  private final WebClient webClient;
  private final LocationService locationService;
  private final TemporalConverter temporalConverter;
  
  @Override
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar
  ) throws CalendarException {
    log.entry(calendar);
    
    LocalDate start = calculateStartOfMonth();
    LocalDate end = start.plusMonths(MONTHS_TO_ADD);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, start, end);

    return log.exit(mapEvents(vEvents, calendar.getOwner()));
  }
  
  private LocalDate calculateStartOfMonth() {
    log.entry();
    
    LocalDate now = LocalDate.now();
    
    return log.exit(LocalDate.of(now.getYear(), now.getMonth(), 1));
  }
  
  @Override
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      @NotNull final LocalDate from
  ) throws CalendarException {
    log.entry(calendar, from);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, from, from.plusMonths(MONTHS_TO_ADD));

    return log.exit(mapEvents(vEvents, calendar.getOwner()));
  }
  
  @Override
  public List<Event> getEvents(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      @NotNull final LocalDate from,
      @NotNull final LocalDate till
  ) throws CalendarException {
    log.entry(calendar, from, till);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, from, till);

    return log.exit(mapEvents(vEvents, calendar.getOwner()));
  }
  
  
  private List<VEvent> fetchEventsFromCalDav(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      @NotNull LocalDate from,
      LocalDate till
  ) throws CalendarException {
    log.entry(webClient, calendar, from, till);
    
    if (webClient == null) {
      log.error("WebClient is not configured.");
    }
    
    List<VEvent> events = new ArrayList<>();
    byte[] calendarBytes = webClient
        .get()
        .uri(calculateCalendarUri(calendar, from, till))
        .headers(calendar::authenticate)
        .retrieve()
        .bodyToMono(byte[].class)
        .block();
   
    if (calendarBytes == null) {
      return events;
    }
    
    events = readEvents(calendarBytes, from, till);
    
    return log.exit(events);
  }
  
  private String calculateCalendarUri(
      @NotNull final de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar calendar,
      final LocalDate from,
      final LocalDate till
  ) {
    log.entry(calendar, from, till);
    
    String result = calendar.getCalendarId() + "?export&expand=1";
    
    if (from != null) {
      result += "&start=" + from.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
    
    if (till != null) {
      result += "&end=" + till.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
    
    return log.exit(result);
  }
  
  private List<VEvent> readEvents(
      @NotNull final byte[] calendarBytes,
      @NotNull final LocalDate from,
      @NotNull final LocalDate till
  ) throws CalendarException {
    log.entry(calendarBytes.length, from, till);
    
    try (InputStream in = new java.io.ByteArrayInputStream(calendarBytes)) {
      Calendar calendar = new CalendarBuilder().build(in);
      
      List<VEvent> events = calendar.getComponents().stream()
                       .map(c -> (VEvent) c)
                       .filter(e -> isBetween(e, from, till))
                       .toList();
      
      log.exit(events.size());
      return events;
    } catch (ParserException | IOException e) {
      throw log.throwing(
          new CalendarException("Could not read the CALDAV events from external service.", e)
      );
    }
  }
  
  private boolean isBetween(
      @NotNull final VEvent event,
      @NotNull final LocalDate from,
      @NotNull final LocalDate till
  ) {
    log.entry(event.getUid().isPresent() ? event.getUid().get().getValue() : "no-uid", from, till);
    
    if (event.getDateTimeStart().isEmpty()) {
      log.debug("Event {} has no start date.", event.getUid());
      return log.exit(true);
    }
    
    LocalDate date = LocalDate.ofInstant(temporalConverter.convertToDatabaseColumn(event.getDateTimeStart().get().getDate()), ZoneId.systemDefault());

    return log.exit(
        (date.isEqual(from) || date.isAfter(from)) &&
        (date.isEqual(till) || date.isBefore(till))
    );
  }
  
  private List<Event> mapEvents(@NotNull List<VEvent> events, @NotBlank final String owner) {
    log.entry(events.size(), owner);
    
    return log.exit(
        events.stream()
            .map(e -> mapper.toEvent(e, locationService))
            .map(e -> (Event)e.toBuilder().owner(owner).build())
            .toList()
    );
  }
}