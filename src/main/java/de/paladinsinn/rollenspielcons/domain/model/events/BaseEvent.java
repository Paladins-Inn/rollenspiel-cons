package de.paladinsinn.rollenspielcons.domain.model.events;


import de.paladinsinn.rollenspielcons.domain.model.HasEtag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;


/**
 * Base class for all events.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Schema(
    title = "BaseEvent for all events",
    description = "This is the base class consisting of the minimal set of attributes for an event.",
    examples = {
        """
        {
          "id": 123456789012345,
          "labels": ["label1", "label2"],
          "startDay": "2024-12-24",
          "startTime": "18:00:00",
          "duration": "PT6H",
          "name": "Event Name",
          "description": "This is a description of the event."
        }
        """,
        """
        {
          "id": 123456789012345,
          "labels": ["label1", "label2"],
          "startDay": "2024-12-24",
          "duration": "PT2D",
          "name": "Event Name",
          "description": "This is a description of the event."
        }
        """,
    }
)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString()
@EqualsAndHashCode(of = {"id"})
public abstract class BaseEvent implements HasEtag {
  /**
   * This is no database sequence but a <a href="https://en.wikipedia.org/wiki/Snowflake_ID">Snowflake ID</a>.
   *
   * We normally use the generator provided by {@link de.paladinsinn.rollenspielcons.config.SnowflakeIdGeneratorConfig}.
   */
  @Schema(
      description = "Unique identifier of the event.",
      comment = "This id has to be generated when the event is created. It is a <a href=\"https://en.wikipedia.org/wiki/Snowflake_ID\">Snowflake ID</a>.",
      examples = { "123456789012345", "987654321098765432" },
      minimum = "1",
      required = true
  )
  @Min(value = 1, message = "The id must be greater than {value}. Please use a Snowflake ID.")
  private long id;

  @Schema(
      description = "The labels for this event.",
      examples = { "[\"label1\", \"label2\"]" },
      minItems = 0,
      maxItems = 20
  )
  @Size(min = 0, max = 20, message = "There must be between {min} and {max} labels.")
  @Builder.Default
  private List<String> labels = new LinkedList<>();
  
  @Schema(
      description = "The start day of the event.",
      examples = { "2024-12-24", "2025-01-01" },
      required = true
  )
  @NotNull(message = "The start day must be present.")
  private LocalDate startDay;
  
  @Schema(
      description = "The start time of the event. If not present, the event is considered to be an all-day event.",
      examples = { "18:00:00", "09:30:00" },
      nullable = true
  )
  private LocalTime startTime;
  @Schema(
      description = "The duration of the event. Must be at least 30 minutes.",
      examples = { "PT6H", "PT2D", "PT45M" },
      nullable = true
  )
  private Duration duration;

  @Schema(
      description = "The name of the event.",
      examples = { "Event Name", "Another Event" },
      minLength = 3,
      maxLength = 100,
      required = true
  )
  @NotNull(message = "The name must be present.")
  @Size(min = 3, max = 100)
  private String name;
  @Schema(
      description = "A description of the event.",
      examples = { "This is a description of the event.", "Another description." },
      maxLength = 4000,
      nullable = false
  )
  @NotNull(message = "The description must be present.")
  @Size(min = 1, max = 4000, message = "The description must be between {min} and {max} characters long.")
  private String description;

  
  @Override
  public String monitoredData() {
    return id
         + labels.stream().reduce("", (a, b) -> a + b)
        + startTime + duration
        + name + description;
  }
}
