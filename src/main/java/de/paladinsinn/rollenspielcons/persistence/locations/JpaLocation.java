package de.paladinsinn.rollenspielcons.persistence.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.Location;
import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaLocation extends AbstractBaseEntity implements Location {
}
