package de.paladinsinn.rollenspielcons.domain.api.time;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.beans.Transient;
import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * An time specification consisting of a start day, an optional start time and an optional duration.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Schema(
    title = "PhysicalEvent event",
    description = "This is a convention event with a locations.",
    examples = {
        """
        {
         "temporalData": {
            "startDay": "2024-12-24",
            "startTime": "18:00:00",
            "duration": "PT5H59M59S"
          }
        }
        """,
        """
        {
          "temporalData": {
            "startDay": "2024-12-24",
            "duration": "PT2D"
          }
        }
        """,
        """
        {
          "temporalData": {
            "startDay": "2024-12-24"
          }
        }
        """
    }
)
public interface Timed {
  @Schema(
      description = "The temporal data for this event.",
      examples = {
          """
          {
            "startDay": "2024-12-24T18:00:00+01:00[Europe/Berlin]",
            "duration": "PT5H59M59S"
          }
          """,
          """
          {
            "startDay": "2024-1200:00:00+01:00[Europe/Berlin]",
            "duration": "PT2D"
          }
          """,
          """
          {
            "startDay": "2024-12-24T18:00:00+01:00[Europe/Berlin]",
          }
          """
      },
      required = true
  )
  TimeSpec getTemporalData();
  
  @JsonIgnore
  @Transient
  default LocalDateTime getStart() {
    return getTemporalData().getStart();
  }
  
  @JsonIgnore
  @Transient
  default LocalDateTime getEnd() {
    return getTemporalData().getEnd();
  }
}
