package de.paladinsinn.rollenspielcons.domain.model.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarType;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public class InMemoryCalendar implements Calendar {
  private long id;
  private String owner;

  @Override
  public URI getCalendarId() {
    return null;
  }
  
  @Override
  public ImporterAuthentication getAuthentication() {
    return null;
  }
  
  @Override
  public CalendarType getSyncType() {
    return null;
  }
  
  @Override
  public SyncSuccess lastSyncResult() {
    return null;
  }
  
  @Override
  public OffsetDateTime lastSyncTime() {
    return null;
  }
  
  @Override
  public OffsetDateTime getLastSyncAttemptTime() {
    return null;
  }
  
  @Override
  public String getDisplayText() {
    return "";
  }
  
  @Override
  public String getUri() {
    return "";
  }
  
}
