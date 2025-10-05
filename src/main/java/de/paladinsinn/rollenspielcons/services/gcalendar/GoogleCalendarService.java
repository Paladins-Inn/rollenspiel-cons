package de.paladinsinn.rollenspielcons.services.gcalendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Events;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
@XSlf4j
public class GoogleCalendarService {
  
  private Calendar service;
  
  @Value("${google.calendar.service-account-key-path}")
  private String serviceAccountKeyPath;
  
  @Value("${google.calendar.id}")
  private String calendarId;
  
  
  @PostConstruct
  public void init() {
    log.entry(serviceAccountKeyPath, calendarId);
    
    try {
      createService();
    } catch (FileNotFoundException e) {
      log.error("The google calendar service won't work. The service account key file not found at path: " + serviceAccountKeyPath, e);
    } catch (Exception e) {
      log.error("Error initializing Google Calendar service", e);
    }
    
    log.exit(service);
  }
  
  private void createService() throws GeneralSecurityException, IOException {
    @SuppressWarnings("deprecation")
    GoogleCredential credential = GoogleCredential
        .fromStream(new ClassPathResource(serviceAccountKeyPath).getInputStream())
        .createScoped(Collections.singleton(CalendarScopes.CALENDAR_READONLY));
    
    service = new Calendar.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),
        credential)
        .setApplicationName("Rollenspiel Cons Kalender")
        .build();
  }
  
  
  @Cacheable("calendarEvents")
  @Counted(value = "google_calendar_requests_count", description = "Total calls to google calendar api")
  @Timed(value = "google_calendar_requests_duration", description = "Time taken to call google calendar api", percentiles = {0.5, 0.95, 0.99})
  public List<Event> getEvents(final LocalDateTime start, final LocalDateTime end) {
    log.entry(service, start, end);
    
    List<Event> result = new ArrayList<>();
    
    if (service == null) {
      log.error("Google Calendar service is not initialized.");
      return log.exit(result);
    }
    
    Events events = loadEventsFromGoogle(start, end);
    result = convertGoogleEvents(events);
    
    return log.exit(result);
  }
  
  private Events loadEventsFromGoogle(final LocalDateTime start, final LocalDateTime end) {
    log.entry(start, end);
    
    Events result;
    
    try {
      DateTime startDateTime = new DateTime(start.toInstant(ZoneOffset.UTC).toEpochMilli());
      DateTime endDateTime = new DateTime(end.toInstant(ZoneOffset.UTC).toEpochMilli());
      
      result = service.events().list(calendarId)
                      .setTimeMin(startDateTime)
                      .setTimeMax(endDateTime)
                      .setOrderBy("startTime")
                      .setSingleEvents(true)
                      .execute();
    } catch (IOException e) {
      log.error("Error retrieving Google Calendar events", e);
    }
    
    return log.exit(result);
  }
  
  private Collection<Event> convertGoogleEvents(final Events events) {
    log.entry(events);
    
    Collection<Event> result = events
        .getItems().stream()
        .map(this::convertGoogleEvent)
        .collect(Collectors.toCollection()
        
    return log.exit(result);
  }
  
  private Event convertGoogleEvent(com.google.api.services.calendar.model.Event event) {
    if (event.getLocation() != null && !event.getLocation().isBlank()) {
      log.debug("This is a physical event at location: {}", event.getLocation());
      
      return convertPhysicalEvent(event);
    } else {
      log.debug("THis is a virtual event (no location specified).");
      
      return convertVirtualEvent(event);
    }
  }
  
  private Event convertPhysicalEvent(com.google.api.services.calendar.model.Event event) {
    log.entry(event);
  }
  
  private Event convertVirtualEvent(com.google.api.services.calendar.model.Event event) {
    log.entry(event);
    
    Map<String, Object> eventMap = new HashMap<>();
    
    eventMap.put("id", event.getId());
    eventMap.put("title", event.getSummary());
    
    // Start und Ende
    if (event.getStart().getDateTime() != null) {
      eventMap.put("start", event.getStart().getDateTime().toStringRfc3339());
      eventMap.put("end", event.getEnd().getDateTime().toStringRfc3339());
      eventMap.put("allDay", false);
    } else {
      eventMap.put("start", event.getStart().getDate().toString());
      eventMap.put("end", event.getEnd().getDate().toString());
      eventMap.put("allDay", true);
    }
    
    // Weitere Eigenschaften
    eventMap.put("description", event.getDescription());
    eventMap.put("location", event.getLocation());
    
    // Farben basierend auf Google Calendar Kategorien
    String colorId = event.getColorId();
    if (colorId != null) {
      switch (colorId) {
        case "1": eventMap.put("backgroundColor", "#7986CB"); break; // Lavendel
        case "2": eventMap.put("backgroundColor", "#33B679"); break; // Salbei
        case "3": eventMap.put("backgroundColor", "#8E24AA"); break; // Traube
        case "4": eventMap.put("backgroundColor", "#E67C73"); break; // Flamingo
        case "5": eventMap.put("backgroundColor", "#F6BF26"); break; // Banane
        case "6": eventMap.put("backgroundColor", "#F4511E"); break; // Mandarine
        case "7": eventMap.put("backgroundColor", "#039BE5"); break; // Pfau
        case "8": eventMap.put("backgroundColor", "#616161"); break; // Graphit
        case "9": eventMap.put("backgroundColor", "#3F51B5"); break; // Blaubeere
        case "10": eventMap.put("backgroundColor", "#0B8043"); break; // Basilikum
        case "11": eventMap.put("backgroundColor", "#D50000"); break; // Tomate
        default: eventMap.put("backgroundColor", "#4285F4"); break; // Standard Blau
      }
    }
    
    // Zusätzliche Metadaten aus Beschreibung extrahieren
    Map<String, Object> extendedProps = new HashMap<>();
    if (event.getDescription() != null) {
      extendedProps.put("description", event.getDescription());
      
      // Optionale strukturierte Daten aus der Beschreibung parsen
      // Beispiel: "Gamemaster: Max Mustermann, Players: 4/6"
      String description = event.getDescription();
      if (description.contains("Gamemaster:")) {
        String gamemaster = description.substring(description.indexOf("Gamemaster:") + 11);
        if (gamemaster.contains(",")) {
          gamemaster = gamemaster.substring(0, gamemaster.indexOf(",")).trim();
        } else {
          gamemaster = gamemaster.trim();
        }
        extendedProps.put("gamemaster", gamemaster);
      }
      
      if (description.contains("Players:")) {
        String players = description.substring(description.indexOf("Players:") + 8).trim();
        if (players.contains("/")) {
          try {
            int currentPlayers = Integer.parseInt(players.substring(0, players.indexOf("/")).trim());
            int maxPlayers = Integer.parseInt(players.substring(players.indexOf("/") + 1).trim());
            
            extendedProps.put("currentPlayers", currentPlayers);
            extendedProps.put("maxPlayers", maxPlayers);
          } catch (NumberFormatException e) {
            log.warn("Could not parse player counts from: {}", players);
          }
        }
      }
    }
    
    eventMap.put("extendedProps", extendedProps);
    
    return eventMap;
  }
}