package de.paladinsinn.rollenspielcons.persistence.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.Location;
import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEvent;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.FetchType.LAZY;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaLocation extends AbstractBaseEntity implements Location {
  @ManyToOne(
      targetEntity = JpaEvent.class,
      fetch = LAZY,
      optional = false
  )
  @JoinColumn(name = "event_id", nullable = false)
  private JpaEvent event;
}
