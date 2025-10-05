package de.paladinsinn.rollenspielcons.domain.persistence.events;


import de.paladinsinn.rollenspielcons.domain.api.events.PhysicalEvent;
import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.persistence.locations.JpaPhysicalAddress;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;


/**
 * A convention event with a locations.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Entity(name = "PhysicalEvent")
@Table(name = "PHYSICALEVENTS")
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaPhysicalEvent extends JpaEvent implements PhysicalEvent {
  @OneToMany(
      targetEntity = JpaPhysicalAddress.class, mappedBy = "event", orphanRemoval = true,
      fetch = EAGER,
      cascade = {PERSIST, MERGE, DETACH, REFRESH, REMOVE}
  )
  @NotNull(message = "At least one location must be set")
  @Size(min = 1, max = 10, message = "A single convention can have between 1 and 10 locations")
  @Builder.Default
  private Set<PhysicalAddress> locations = new HashSet<>(10);
}
