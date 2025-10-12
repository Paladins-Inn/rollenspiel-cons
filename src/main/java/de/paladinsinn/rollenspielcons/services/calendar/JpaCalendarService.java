package de.paladinsinn.rollenspielcons.services.calendar;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.api.time.TimeSpec;
import de.paladinsinn.rollenspielcons.domain.model.time.TimeSpecImpl;
import de.paladinsinn.rollenspielcons.persistence.events.EventRepository;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEvent;
import de.paladinsinn.rollenspielcons.persistence.time.EmbeddableTimeSpec;
import de.paladinsinn.rollenspielcons.services.api.CalendarException;
import de.paladinsinn.rollenspielcons.services.api.CalendarService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Service
@RequiredArgsConstructor
@XSlf4j
public class JpaCalendarService implements CalendarService {
  private final EventRepository eventRepository;
  
  private final EventMap
  
  @Override
  public List<Event> getEvents(final Calendar calendar) throws CalendarException {
    log.entry(calendar);
    
    LinkedList<Event> result = new LinkedList<>();
    
    eventRepository.findAll(Sort.by("startDay")).forEach(result::add);
    
    return log.exit(result);
  }
  
  @Override
  public List<Event> getEvents(final Calendar calendar, final LocalDate from)
      throws CalendarException {
    log.entry(calendar);
    
    List<JpaEvent> result;
    
    EmbeddableTimeSpec timestpec = calculateTimeSpec(from);
    
    result = eventRepository.findAllByTemporalDataIsAfter(timestpec);
    
    return log.exit(result.stream().map());
  }
  
  private EmbeddableTimeSpec calculateTimeSpec(final LocalDate from) {
    log.entry(from);
    
    TimeSpec result = TimeSpecImpl
        .builder()
        .startDay(LocalDate.now().atStartOfDay(ZoneId.systemDefault()))
        .build();
    
    return log.exit(result);
  }
  
  @Override
  public List<Event> getEvents(final Calendar calendar, final LocalDate from, final LocalDate till)
      throws CalendarException {
    return List.of();
  }
}
