package de.paladinsinn.rollenspielcons.domain.model;


import jakarta.validation.constraints.Min;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
public interface HasId {
  /**
   * This is no database sequence but a <a href="https://en.wikipedia.org/wiki/Snowflake_ID">Snowflake ID</a>.
   *
   * <p>We normally use the generator provided by {@link de.paladinsinn.rollenspielcons.config.SnowflakeIdGeneratorConfig}.</p>
   */
  @Schema(
      description = "Unique identifier of the event.",
      comment = "This id has to be generated when the event is created. It is a <a href=\"https://en.wikipedia.org/wiki/Snowflake_ID\">Snowflake ID</a>.",
      examples = { "123456789012345", "987654321098765432" },
      minimum = "1",
      required = true
  )
  @Min(value = 1, message = "The id must be greater than {value}. Please use a Snowflake ID.")
  long getId();
}
