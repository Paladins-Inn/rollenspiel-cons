package de.paladinsinn.rollenspielcons.domain.model.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarType;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import java.io.Serial;
import java.time.OffsetDateTime;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ICalCalendar extends AbstractModelBase implements Calendar {
  @Serial
  private static final long serialVersionUID = 1L;
  
  private String owner;
  
  private String calendarId;
  
  private ImporterAuthentication authentication;
  
  private CalendarType syncType;
  private SyncSuccess lastSyncResult;
  private Duration syncPeriod;
  private OffsetDateTime lastSyncAttemptTime;
  private OffsetDateTime lastSyncTime;
}
