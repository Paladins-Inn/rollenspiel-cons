package de.paladinsinn.rollenspielcons.persistence.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarType;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static de.paladinsinn.rollenspielcons.domain.api.SystemConstants.URL_MAX_LENGTH;
import static de.paladinsinn.rollenspielcons.domain.api.SystemConstants.URL_MIN_LENGTH;
import static de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess.NEVER;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;


/**
 * The base data for all imported calendar definitions.
 *
 * <p>This is an abstract class because the authentication methods differ from import to import.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Entity(name = "Calendar")
@Table(name = "CALENDARS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(
    name = "CALENDAR_TYPE",
    discriminatorType = jakarta.persistence.DiscriminatorType.STRING,
    length = 20
)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class AbstractJpaCalendar extends AbstractBaseEntity implements Calendar {
  @Serial
  private static final long serialVersionUID = 1L;
  
  /**
   * The type of the calendar.
   * Will be the discriminator column.
   * It has to match the values in {@link CalendarType}.
   */
  @Enumerated(STRING)
  @Column(name = "CALENDAR_TYPE", insertable = false, updatable = false)
  @ToString.Include
  private String syncTypeValue;
  
  @Override
  public CalendarType getSyncType() {
    return CalendarType.valueOf(syncTypeValue);
  }
  
  @Column(name = "SYNC_URI", nullable = false)
  @Schema(
      description = "The URI to sync the calendar with.",
      examples = {"https://example.com/calendar"}
  )
  @NotNull(message = "The sync URI must be set.")
  @Size(min = URL_MIN_LENGTH, max = URL_MAX_LENGTH, message = "The sync URI must be between {min} and {max} characters long.")
  @ToString.Include
  private URI syncUri;

  @Column(name = "LAST_SYNC_TIME")
  @Schema(
      description = "Last successful synchronization time.",
      examples = {
          "2024-12-24T18:00:00+01:00[Europe/Berlin]",
          "2024-12-24T18:00:00+01:00",
          "2024-12-24T16:00:00Z"
      },
      format = "date-time",
      nullable = true
  )
  @Nullable
  @ToString.Include
  private OffsetDateTime lastSyncTime;
  
  @Column(name = "LAST_SYNC_ATTEMPT_TIME")
  @Schema(
      description = "Timestamp of the last synchronization attempt.",
      examples = {
          "2024-12-24T18:00:00+01:00[Europe/Berlin]",
          "2024-12-24T18:00:00+01:00",
          "2024-12-24T16:00:00Z"
      },
      format = "date-time",
      nullable = true
  )
  @Nullable
  @ToString.Include
  private OffsetDateTime lastSyncAttemptTime;

  @Column(name = "LAST_SYNC_RESULT", nullable = false)
  @Enumerated(value = STRING)
  @NotNull(message = "The last sync result must be set.")
  @Builder.Default
  @ToString.Include
  private SyncSuccess lastSyncResult = NEVER;
}
