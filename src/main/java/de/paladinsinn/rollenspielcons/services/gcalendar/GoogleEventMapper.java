package de.paladinsinn.rollenspielcons.services.gcalendar;


import com.google.api.client.util.DateTime;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;

/**
 * Mapper for Google Calendar Events and the domain Event.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-04
 */
@Mapper(componentModel = "spring")
public interface GoogleEventMapper {
  /**
   * Maps a Google Calendar Event to the domain Event.
   *
   * @param event The Google Calendar event.
   * @return The Paladins Inn Event.
   */
  @Mapping(target = "id", source = "id")
  @Mapping(target = "title", source = "summary")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "location", source = "location")
  @Mapping(target = "start", expression = "java(toLocalDateTime(event.getStart()))")
  @Mapping(target = "end", expression = "java(toLocalDateTime(event.getEnd()))")
  Event toDomainEvent(com.google.api.services.calendar.model.Event event);
  
  default LocalDateTime toLocalDateTime(
      com.google.api.services.calendar.model.EventDateTime eventDateTime
  ) {
    if (eventDateTime == null) {
      return null;
    }
    
    DateTime dateTime = eventDateTime.getDateTime();
    if (dateTime != null) {
      return LocalDateTime.ofInstant(
          Instant.ofEpochMilli(dateTime.getValue()),
          ZoneOffset.UTC
      );
    }
    
    // Für Ganztagstermine
    if (eventDateTime.getDate() != null) {
      return eventDateTime.getDate().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }
    
    return null;
  }
  
  
  /**
   * Maps a domain Event to a Google Calendar Event.
   *
   * @param event The Paladins Inn Event.
   * @return The Google Calendar event.
   */
  @Mapping(target = "id", source = "id")
  @Mapping(target = "summary", source = "title")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "location", source = "location")
  @Mapping(target = "start", expression = "java(toEventDateTime(event.getStart()))")
  @Mapping(target = "end", expression = "java(toEventDateTime(event.getEnd()))")
  com.google.api.services.calendar.model.Event toGoogleEvent(Event event);
  
  default com.google.api.services.calendar.model.EventDateTime toEventDateTime(
      java.time.LocalDateTime localDateTime
  ) {
    if (localDateTime == null) {
      return null;
    }
    
    com.google.api.client.util.DateTime dateTime =
        new com.google.api.client.util.DateTime(localDateTime.atZone(java.time.ZoneOffset.UTC).toInstant().toEpochMilli());

    com.google.api.services.calendar.model.EventDateTime eventDateTime =
        new com.google.api.services.calendar.model.EventDateTime();

    eventDateTime.setDateTime(dateTime);
    return eventDateTime;
  }
}