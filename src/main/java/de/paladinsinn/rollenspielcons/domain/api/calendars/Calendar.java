package de.paladinsinn.rollenspielcons.domain.api.calendars;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.calendars.InMemoryCalendar;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@JsonDeserialize(as = InMemoryCalendar.class)
public interface Calendar extends HasId, HasOwner, HasDisplayText, Serializable {
  /**
   * This is the calender to sync from.
   * Since every integration is different, it is a simple string.
   * For google it resembles an email address.
   * For CALDAV this is the full URL of the calendar.
   *
   * @return The Calendar to sync from.
   */
  String getCalendarId();
  
  
  /**
   * @return The authentication information to access the calendar.
   */
  ImporterAuthentication getAuthentication();
  
  /**
   * @return Returns the type of the calendar to be synced.
   */
  CalendarType getSyncType();
  
  /**
   * @return The outcome of the last synchronization attempt.
   */
  SyncSuccess getLastSyncResult();
  
  /**
   * @return The timestamp of the last synchronization.
   */
  OffsetDateTime getLastSyncTime();
  
  /**
   * @return The timestamp of the last syncrhonization attempt.
   */
  OffsetDateTime getLastSyncAttemptTime();
}
