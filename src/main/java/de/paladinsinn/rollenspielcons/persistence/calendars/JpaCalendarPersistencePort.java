package de.paladinsinn.rollenspielcons.persistence.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarPersistenceException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarPersistencePort;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarType;
import de.paladinsinn.rollenspielcons.domain.model.calendars.ICalCalendar;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * The port for calendar jpa persistence in a hexoganal architecture.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JpaCalendarPersistencePort implements CalendarPersistencePort {
  private final JpaCalendarRepository repository;
  private final CalendarMapper mapper;
  
  @Override
  public Calendar create(final Calendar calendar) throws CalendarPersistenceException {
    log.trace("enter -  {}", calendar);
    
    Calendar result = saveCalendar(calendar, mapper.toJpa((ICalCalendar) calendar));
    
    log.trace("exit - {}", result);
    return result;
  }
  
  private @NotNull Calendar saveCalendar(final Calendar calendar, final JpaCalDavCalendar jpa)
      throws CalendarPersistenceException {
    log.trace("enter -  {}, {}", calendar, jpa);
    
    Calendar result;
    
    try {
      result = repository.save(jpa);
      log.debug("Saved calendar. calendar={}", result);
    } catch (Exception e) {
      log.warn("Could not save calendar " + calendar.getDisplayText()
               + " (" + calendar.getId() + ").", e);
      
      CalendarPersistenceException exception = new CalendarPersistenceException(
          calendar,
          "Could not create calendar " + calendar.getDisplayText() + ". " + e.getMessage()
      );
      log.error("Throwing exception", exception);
      throw exception;
    }
    
    log.trace("exit - {}", result);
    return result;
  }
  
  @Override
  public Optional<Calendar> retrieveById(final Long id) {
    log.trace("enter -  {}", id);
    
    Optional<AbstractJpaCalendar> jpa = repository.findById(id);
    jpa.ifPresent(
        j -> log.debug("Loaded calendar by id. calendar={}", j)
    );
    
    Optional<Calendar> result = jpa.map(mapper::toDomain);
    
    log.trace("exit - {}", result);
    return result;
  }
  
  @Override
  public List<Calendar> retrieveAll() {
    log.trace("enter - ");
    
    Iterable<AbstractJpaCalendar> jpa = repository.findAll();
    log.debug("Loaded all calendars. calendar.hasEntries={}", jpa.iterator().hasNext());
    
    List<Calendar> result = StreamSupport.stream(jpa.spliterator(), false)
            .map(c -> (Calendar)mapper.toDomain(c))
            .toList();

    log.trace("exit - {}", result);
    return result;
  }
  
  @Override
  public List<Calendar> retrieveByOwner(final String owner) {
    log.trace("enter -  {}", owner);
    
    List<AbstractJpaCalendar> jpa = repository.findByOwner_Owner(owner);
    log.debug("Loaded calendar by owner. owner={} calendar.count={}", owner, jpa.size());
    
    List<Calendar> result = jpa.stream().map(c -> (Calendar)mapper.toDomain(c)).toList();

    log.trace("exit - {}", result);
    return result;
  }
  
  @Override
  public List<Calendar> retrieveByType(final CalendarType type) {
    log.trace("enter -  {}", type);
    
    List<AbstractJpaCalendar> jpa = repository.findBySynctypevalue(type.name());
    log.debug("Loaded calendar by type. type={} calendar.count={}", type, jpa.size());
    
    List<Calendar> result = jpa.stream().map(c -> (Calendar)mapper.toDomain(c)).toList();

    log.trace("exit - {}", result);
    return result;
  }
  
  
  @Override
  public void update(final Calendar calendar) throws CalendarPersistenceException {
    log.trace("enter -  {}", calendar);
    
    Calendar result = saveCalendar(calendar, mapper.toJpa((ICalCalendar) calendar));
    log.debug("Updated calendar. orig={}, updated={}", calendar, result);
    
    log.trace("exit -");
  }
  
  
  @Override
  public void delete(@NotNull  final Calendar calendar) throws CalendarPersistenceException {
    log.trace("enter -  {}", calendar);
    
    try {
      repository.deleteById(calendar.getId());
      log.debug("Deleted calendar. calendar={}", calendar);
    } catch (Exception e) {
      log.warn("Could not delete calendar " + calendar.getDisplayText()
               + " (" + calendar.getId() + ").", e);
      
      throw new CalendarPersistenceException(
          calendar,
          "Could not delete calendar " + calendar.getDisplayText() + ". " + e.getMessage()
      );
    }
    
    log.trace("Exiting method");
  }
  
  @Override
  public void delete(final Long id) throws CalendarPersistenceException {
    log.trace("enter -  {}", id);
    
    Calendar calendar = retrieveById(id).orElse(null);
    
    if (calendar != null) {
      delete(calendar);
    }
    
    log.trace("exit -");
  }
  
  
}
