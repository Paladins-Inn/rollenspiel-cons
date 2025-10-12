package de.paladinsinn.rollenspielcons.domain.model.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarType;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import de.paladinsinn.rollenspielcons.domain.api.integrations.RefreshTokenAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import java.io.Serial;
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
 * @since 2025-10-12
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class GoogleCalendar extends AbstractModelBase implements Calendar {
  @Serial
  private static final long serialVersionUID = 1L;
  
  private String owner;
  
  private String calendarId;
  
  private RefreshTokenAuthentication authentication;
  
  private CalendarType syncType;
  private SyncSuccess lastSyncResult;
  private OffsetDateTime lastSyncAttemptTime;
  private OffsetDateTime lastSyncTime;
}
