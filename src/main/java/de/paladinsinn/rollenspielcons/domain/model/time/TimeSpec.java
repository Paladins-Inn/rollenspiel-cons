package de.paladinsinn.rollenspielcons.domain.model.time;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import java.beans.Transient;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * The temporal data of an event.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Schema(
    title = "TimeSpec",
    description = "This is a time specification consisting of a start day, an optional start time "
                  + "and an optional duration.",
    examples = {
        """
        {
          "startDay": "2024-12-24T18:00:00+01:00[Europe/Berlin]",
          "duration": "PT5H59M59S"
        }
        """,
        """
        {
          "startDay": "2024-12-24T00:00:00+01:00[Europe/Berlin]",
          "duration": "PT2D"
        }
        """,
        """
        {
          "startDay": "2024-12-24T18:00:00+01:00[Europe/Berlin]"
        }
        """
    }
)
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TimeSpec {
  /**
   * The start day of the event. The time part is optional. If no time part is given, the start of
   * the day (00:00:00) is assumed.
   */
  @Schema(
      description = "The start day of the event.",
      examples = {"2024-12-24", "2025-01-01"},
      required = true
  )
  @NotNull(message = "The start day must be present.")
  ZonedDateTime startDay;
  
  /**
   * The duration of the event. Must be at least 30 minutes. If no duration is given, the event
   * ends at the end of the start day (23:59:59).
   */
  @Schema(
      description = "The duration of the event. Must be at least 30 minutes.",
      examples = {"PT6H", "PT2D", "PT45M"},
      nullable = true
  )
  Duration duration;
  
  /**
   * The start of the event is the start day with the time part. If no time part is given, the
   * start of the day (00:00:00) is returned.
   *
   * @return the start of the event
   */
  @JsonIgnore
  @Transient
  public LocalDateTime getStart() {
    return LocalDateTime.from(startDay);
  }
  
  /**
   * The end of the event is calculated by adding the duration to the start time. If no duration
   * is given, the end of the day (23:59:59) is returned.
   *
   * @return the end of the event
   */
  @JsonIgnore
  @Transient
  public LocalDateTime getEnd() {
    if (duration != null) {
      return getStart().plus(duration);
    } else {
      return startDay.toLocalDate().atTime(LocalTime.MAX);
    }
  }
}
