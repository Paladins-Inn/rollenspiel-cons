package de.paladinsinn.rollenspielcons.domain.model.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.Location;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @__(@Deprecated))
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public abstract class LocationImpl implements Location {
  @Min(value = 1, message = "The id must be greater than {value}. Please use a Snowflake ID.")
  private long id;
}
