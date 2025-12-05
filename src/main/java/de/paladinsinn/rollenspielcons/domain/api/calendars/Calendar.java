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

package de.paladinsinn.rollenspielcons.domain.api.calendars;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.HasVersion;
import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.calendars.ICalCalendar;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;
import org.springframework.http.HttpHeaders;

import static de.paladinsinn.rollenspielcons.domain.api.SystemConstants.URL_MAX_LENGTH;
import static de.paladinsinn.rollenspielcons.domain.api.SystemConstants.URL_MIN_LENGTH;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@JsonDeserialize(as = ICalCalendar.class)
public interface Calendar extends HasId, HasVersion, HasOwner, HasDisplayText, Serializable {
  /**
   * This is the calender to sync from.
   * Since every integration is different, it is a simple string.
   * For google it resembles an email address.
   * For CALDAV this is the full URL of the calendar.
   *
   * @return The Calendar to sync from.
   */
  @Schema(
      description = "The calendar id. May be somethink resembling an email address (e.g. Google Calendar) or an URI (e.g. Nexcloud iCal via CalDAV) or something completely different. Will be handled by the importer.",
      examples = {
          "local_part@fulle.qualified.domain",
          "https://example.com/calendar"
      }
  )
  @NotNull(message = "The sync URI must be set.")
  @Size(min = URL_MIN_LENGTH, max = URL_MAX_LENGTH, message = "The sync URI must be between {min} and {max} characters long.")
  String getCalendarId();
  
  /**
   * @return Returns the type of the calendar to be synced.
   */
  @NotNull(message = "The sync type must be set.")
  CalendarType getSyncType();
  
  /**
   * @return The authentication information to access the calendar.
   */
  ImporterAuthentication getAuthentication();
  
  /**
   * Authenticates the given http headers.
   * @param httpHeaders The headers to authenticate.
   * @return The authenticated headers.
   * @throws UnsupportedOperationException if the authentication is not supported by the importer.
   */
  default HttpHeaders authenticate(final HttpHeaders httpHeaders) {
    return getAuthentication().authenticate(httpHeaders);
  }
  
  /**
   * @return The outcome of the last synchronization attempt.
   */
  @NotNull(message = "The last sync result must be set.")
  SyncSuccess getLastSyncResult();
  
  /**
   * @return The timestamp of the last synchronization.
   */
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
  OffsetDateTime getLastSyncTime();
  
  /**
   * @return The timestamp of the last syncrhonization attempt.
   */
  @Schema(
      description = "Timestamp of last attempted synchronization.",
      examples = {
          "2024-12-24T18:00:00+01:00[Europe/Berlin]",
          "2024-12-24T18:00:00+01:00",
          "2024-12-24T16:00:00Z"
      },
      format = "date-time",
      nullable = true
  )
  @Nullable
  OffsetDateTime getLastSyncAttemptTime();
  
  @Schema(
      description = "Synchronization interval. Not every calendar needs to be synched in the same period.",
      examples = {"P1D", "P1W", "P1M"}
  )
  @NotNull
  Duration getSyncPeriod();
}
