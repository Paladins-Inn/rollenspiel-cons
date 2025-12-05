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

package de.paladinsinn.rollenspielcons.domain.api.time;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.model.time.TimeSpecImpl;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = TimeSpecImpl.class)
public interface TimeSpec {
  /**
   * The start day of the event. The time part is optional. If no time part is given, the start of
   * the day (00:00) is assumed.
   *
   * @return the start day of the event, never null
   */
  ZonedDateTime getStartDay();
  
  /**
   * The duration of the event. If no duration is given, the event is considered to be a point in
   * time.
   *
   * @return the duration of the event, may be null
   */
  Duration getDuration();
  
  /**
   * The start of the event is the start day with the time part. If no time part is given, the
   * start of the day (00:00:00) is returned.
   *
   * @return the start of the event
   */
  default LocalDateTime getStart() {
    return LocalDateTime.from(getStartDay());
  }
  
  /**
   * The end of the event is calculated by adding the duration to the start time. If no duration
   * is given, the end of the day (23:59:59) is returned.
   *
   * @return the end of the event
   */
  default LocalDateTime getEnd() {
    if (getDuration() != null) {
      return getStart().plus(getDuration());
    } else {
      return getStartDay().toLocalDate().atTime(LocalTime.MAX);
    }
  }
}
