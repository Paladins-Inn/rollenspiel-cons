/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.paladinsinn.rollenspielcons.persistence.calendars;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.calendars.CalendarType;
import de.paladinsinn.rollenspielcons.domain.api.calendars.SyncSuccess;
import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.time.OffsetDateTime;
import java.time.Duration;
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
  @Column(name = "CALENDAR_TYPE", insertable = false, updatable = false)
  @ToString.Include
  private String synctypevalue;
  
  @Override
  public CalendarType getSyncType() {
    return CalendarType.valueOf(synctypevalue);
  }
  
  @Column(name = "CALENDAR_ID", nullable = false)
  @NotBlank(message = "The calendar ID must be set.")
  @Size(min = URL_MIN_LENGTH, max = URL_MAX_LENGTH, message = "The calendar ID must be between {min} and {max} characters long.")
  private String calendarId;
  
  @Column(name = "SYNC_PERIOD", nullable = false)
  @NotNull(message = "The sync period must be set.")
  private Duration syncPeriod;

  @Column(name = "LAST_SYNC_TIME")
  @Nullable
  private OffsetDateTime lastSyncTime;
  
  @Column(name = "LAST_SYNC_ATTEMPT_TIME")
  @Nullable
  private OffsetDateTime lastSyncAttemptTime;

  @Column(name = "LAST_SYNC_RESULT", nullable = false)
  @Enumerated(value = STRING)
  @NotNull(message = "The last sync result must be set.")
  @Builder.Default
  @ToString.Include
  private SyncSuccess lastSyncResult = NEVER;
}
