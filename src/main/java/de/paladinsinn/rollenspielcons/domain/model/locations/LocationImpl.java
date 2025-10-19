package de.paladinsinn.rollenspielcons.domain.model.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.Location;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public abstract class LocationImpl implements Location {
  @Min(value = 1, message = "The id must be greater than {value}. Please use a Snowflake ID.")
  private long id;
  
  @Min(value = 0, message = "The version must be greater than or equal to {value}.")
  private int version;
  
  @NotNull(message = "The Owner has to be set")
  @NotBlank(message = "The Owner has to be non-empty")
  private String owner;
}
