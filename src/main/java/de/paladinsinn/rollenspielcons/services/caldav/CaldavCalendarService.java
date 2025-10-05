package de.paladinsinn.rollenspielcons.services.caldav;

import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.services.api.CalendarException;
import de.paladinsinn.rollenspielcons.services.api.CalendarService;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Location;
import org.springframework.beans.factory.annotation.Value;
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
  
  @Value("${caldav.url:-}")
  private String calendarURL;
  @Value("${caldav.username:-}")
  private String username;
  @Value("${caldav.password:-}")
  private String password;
  
  private final VEventMapper mapper;
  private final WebClient webClient;
  
  @Override
  public List<Event> getEvents(@NotBlank final String calendar) throws CalendarException {
    log.entry(calendar);
    
    LocalDate start = calculateStartOfMonth();
    LocalDate end = start.plusMonths(MONTHS_TO_ADD);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, start, end);

    return log.exit(mapEvents(vEvents));
  }
  
  private LocalDate calculateStartOfMonth() {
    log.entry();
    
    LocalDate now = LocalDate.now();
    
    return log.exit(LocalDate.of(now.getYear(), now.getMonth(), 1));
  }
  
  @Override
  public List<Event> getEvents(@NotBlank final String calendar, @NotNull final LocalDate from)
      throws CalendarException {
    log.entry(calendar, from);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, from, from.plusMonths(MONTHS_TO_ADD));

    return log.exit(mapEvents(vEvents));
  }
  
  @Override
  public List<Event> getEvents(@NotBlank final String calendar, @NotNull final LocalDate from, @NotNull final LocalDate till)
      throws CalendarException {
    log.entry(calendar, from, till);
    
    List<VEvent> vEvents = fetchEventsFromCalDav(calendar, from, till);

    return log.exit(mapEvents(vEvents));
  }
  
  
  private List<VEvent> fetchEventsFromCalDav(@NotBlank final String calendar, @NotNull LocalDate from, LocalDate till) throws CalendarException {
    log.entry(calendar, from, till);
    
    List<VEvent> events = new ArrayList<>();
    byte[] calendarBytes = webClient
        .get()
        .uri(calculateCalendarUri(calendar, from, till))
        .headers(h -> h.setBasicAuth(username, password))
        .retrieve()
        .bodyToMono(byte[].class)
        .block();
    
    events = readEvents(calendarBytes);
    
    return log.exit(events);
  }
  
  private String calculateCalendarUri(@NotBlank final String calendar, final LocalDate from, final LocalDate till) throws CalendarException {
    log.entry(calendar, from, till);
    
    String result = (calendarURL.endsWith("/") ? calendarURL : calendarURL + "/") + username + "/"
                    + calendar + "?export&expand=1";
    
    if (from != null) {
      result += "&start=" + from.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
    
    if (till != null) {
      result += "&end=" + till.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }
    
    return log.exit(result);
  }
  
  private List<VEvent> readEvents(final byte[] calendarBytes) throws CalendarException {
    log.entry(calendarBytes.length);
    
    List<VEvent> events = new ArrayList<>();
    
    if (calendarBytes != null) {
      try (InputStream in = new java.io.ByteArrayInputStream(calendarBytes)) {
        Calendar calendar  = new CalendarBuilder().build(in);
        for (Object obj : calendar.getComponents("VEVENT")) {
          events.add((VEvent) obj);
        }
      } catch (ParserException | IOException e) {
        log.warn(e.getClass().getSimpleName() + " while reading calendar events from CALDAV service.", e.getMessage());
        throw new CalendarException("Could not read the CALDAV events from external service.", e);
      }
    }
    
    return log.exit(events);
  }
  
  private List<Event> mapEvents(List<VEvent> events) {
    log.entry(events);
    
    List<Event> result = new ArrayList<>();
    for (VEvent e : events) {
      String location = e.getLocation().orElse(new Location()).getValue();
      if (location != null && !location.startsWith("http")) {
        result.add(mapper.toPhysicalEvent(e));
      } else {
        result.add(mapper.toWebEvent(e));
      }
    }
    
    return log.exit(result);
  }
  
}