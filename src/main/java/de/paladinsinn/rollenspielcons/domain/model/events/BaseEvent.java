package de.paladinsinn.rollenspielcons.domain.model.events;


import de.paladinsinn.rollenspielcons.domain.model.HasEtag;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/**
 * Base class for all events.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@SuperBuilder(toBuilder = true)
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
  private final long id;
  
  private final List<String> labels;
  
  private final LocalDate startDay;
  private final LocalTime startTime;
  private final Duration duration;

  private final String name;
  private final String description;
  
  @Override
  public String monitoredData() {
    return id
         + labels.stream().reduce("", (a, b) -> a + b)
        + startTime + duration
        + name + description;
  }
}
