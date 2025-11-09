package de.paladinsinn.rollenspielcons.services.caldav;


import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarImportException;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.model.calendars.ICalCalendar;
import de.paladinsinn.rollenspielcons.domain.model.importers.NullAuthentication;
import de.paladinsinn.rollenspielcons.persistence.calendars.CalendarMapper;
import de.paladinsinn.rollenspielcons.persistence.calendars.JpaCalendarPersistencePort;
import de.paladinsinn.rollenspielcons.persistence.calendars.JpaGoogleCalendar;
import de.paladinsinn.rollenspielcons.persistence.events.EventMapper;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEvent;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEventRepository;
import de.paladinsinn.rollenspielcons.persistence.importers.ImportAuthenticationMapper;
import de.paladinsinn.rollenspielcons.persistence.locations.LocationMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.DisplayTextMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.ExternalIdMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.OwnerMapper;
import jakarta.persistence.OptimisticLockException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-09
 */
@ContextConfiguration(classes = ICalImportCalendarServiceTest.TestConfig.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
public class ICalImportCalendarServiceTest {

  private ICalCalendarService client;
  private JpaCalendarPersistencePort calendarPort;
  private JpaEventRepository eventRepository;
  private EventMapper eventMapper;
  
  private ICalImportCalendarService service;
  
  
  @BeforeEach
  void setUp() {
    client = mock(ICalCalendarService.class);
    calendarPort = mock(JpaCalendarPersistencePort.class);
    eventRepository = mock(JpaEventRepository.class);
    eventMapper = mock(EventMapper.class);
    
    service = new ICalImportCalendarService(client, calendarPort, eventRepository, eventMapper);
  }
  
  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(client, calendarPort, eventRepository, eventMapper);
    
    client = null;
    calendarPort = null;
    eventRepository = null;
    eventMapper = null;
    
    service = null;
  }

  @Test
  void shouldBeOkWhenEverythingRunsSmoothly() throws Exception {
    ICalCalendar cal = ICalCalendar
        .builder()
        .calendarId("test")
        .authentication(new NullAuthentication())
        .owner("klenkes74")
        .syncPeriod(Duration.ofDays(1L))
        .lastSyncResult(SyncSuccess.NEVER)
        .build();
   
    when(calendarPort.retrieveById(any())).thenReturn(Optional.of(cal));

    // events and mapping
    var event = mock(Event.class);
    var jpaEvent = mock(JpaEvent.class);
    when(client.getEvents(cal)).thenReturn(List.of(event));
    when(eventMapper.toJpaEvent(event)).thenReturn(jpaEvent);
    when(eventRepository.saveAll(any())).thenReturn(List.of(jpaEvent));

    // execute
    service.importCalendar(cal);

    // verify persistence update called with object returned by builder.build()
    verify(calendarPort, times(1)).update(any());
    verify(eventRepository).saveAll(any());
  }

  @Test
  void shouldThrowExceptionWhenCalendarNotFoundInPersistenceLayer() throws Exception {
    ICalCalendar cal = ICalCalendar
        .builder()
        .calendarId("test")
        .authentication(new NullAuthentication())
        .owner("klenkes74")
        .syncPeriod(Duration.ofDays(1L))
        .lastSyncResult(SyncSuccess.NEVER)
        .build();
    
    when(calendarPort.retrieveById(any())).thenReturn(Optional.empty());
    
    // execute
    try {
      service.importCalendar(cal);
      
      fail("There should have been an exeption since there is no calendar found!");
    } catch (CalendarImportException e) {
      log.info("Success. Caught exception as expected.", e);
      // success
    }
  }
  
  
  @Test
  void shouldThrowExceptionWhenCalendarTypeIsNotICal() throws Exception {
    JpaGoogleCalendar cal = mock(JpaGoogleCalendar.class);
    
    // execute
    try {
      service.importCalendar(cal);
      
      fail("There should have been an exeption since there is no calendar found!");
    } catch (CalendarImportException e) {
      log.info("Success. Caught exception as expected.", e);
      // success
    }
  }
  
  
  @Test
  void shouldThrowExceptionWhenEventsCouldNotBeRetrieved() throws Exception {
    ICalCalendar cal = ICalCalendar
        .builder()
        .calendarId("test")
        .authentication(new NullAuthentication())
        .owner("klenkes74")
        .syncPeriod(Duration.ofDays(1L))
        .lastSyncResult(SyncSuccess.NEVER)
        .build();
    
    when(calendarPort.retrieveById(any())).thenReturn(Optional.of(cal));
    when(client.getEvents(cal)).thenThrow(new CalendarImportException(cal, "Could not retrieve events!"));
    
    
    // execute
    try {
      service.importCalendar(cal);
      
      fail("There should have been an exeption since there is no calendar found!");
    } catch (CalendarImportException e) {
      verify(calendarPort, times(1)).update(any());

      log.info("Success. Caught exception as expected.", e);
      // success
    }
  }
  
  @Test
  void shouldThrowExceptionWhenPersistingThrowsIllegalArgumentException() throws Exception {
    ICalCalendar cal = ICalCalendar
        .builder()
        .calendarId("test")
        .authentication(new NullAuthentication())
        .owner("klenkes74")
        .syncPeriod(Duration.ofDays(1L))
        .lastSyncResult(SyncSuccess.NEVER)
        .build();
    
    when(calendarPort.retrieveById(any())).thenReturn(Optional.of(cal));
    
    // events and mapping
    var event = mock(Event.class);
    var jpaEvent = mock(JpaEvent.class);
    when(client.getEvents(cal)).thenReturn(List.of(event));
    when(eventMapper.toJpaEvent(event)).thenReturn(jpaEvent);
    when(eventRepository.saveAll(any())).thenThrow(new IllegalArgumentException("Could not persist events!"));
    
    // execute
    try {
      service.importCalendar(cal);

      fail("There should have been an exeption because persistence is failing!");
    } catch (CalendarImportException e) {
      verify(calendarPort, times(1)).update(any());

      log.info("Success. Caught exception as expected.", e);
      // success
    }
    
  }
  
  
  @Test
  void shouldThrowExceptionWhenPersistingThrowsOptimisticLockingException() throws Exception {
    ICalCalendar cal = ICalCalendar
        .builder()
        .calendarId("test")
        .authentication(new NullAuthentication())
        .owner("klenkes74")
        .syncPeriod(Duration.ofDays(1L))
        .lastSyncResult(SyncSuccess.NEVER)
        .build();
    
    when(calendarPort.retrieveById(any())).thenReturn(Optional.of(cal));
    
    // events and mapping
    var event = mock(Event.class);
    var jpaEvent = mock(JpaEvent.class);
    when(client.getEvents(cal)).thenReturn(List.of(event));
    when(eventMapper.toJpaEvent(event)).thenReturn(jpaEvent);
    when(eventRepository.saveAll(any())).thenThrow(new OptimisticLockException("Could not persist events!"));
    
    // execute
    try {
      service.importCalendar(cal);
      
      fail("There should have been an exeption because persistence is failing!");
    } catch (CalendarImportException e) {
      verify(calendarPort, times(1)).update(any());
      
      log.info("Success. Caught exception as expected.", e);
      // success
    }
  }
  
  @Test
  void shouldThrowNullPointerExceptionIfNullIsSentAsCalendar() throws CalendarException {
    try {
      service.importCalendar(null);
      
      fail("There should be a NullPointerException!");
    } catch (NullPointerException e) {
      log.info("Caught exception as expected.", e);
    }
  }
  
  @Configuration
  @Import({
      CalendarMapper.class,
      OwnerMapper.class,
      ImportAuthenticationMapper.class,
      EventMapper.class,
      LocationMapper.class,
      DisplayTextMapper.class,
      ExternalIdMapper.class
  })
  public static class TestConfig {}
}