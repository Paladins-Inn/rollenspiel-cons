package de.paladinsinn.rollenspielcons.persistence.events;


import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.persistence.locations.JpaPhysicalAddress;
import de.paladinsinn.rollenspielcons.persistence.locations.JpaWebLocation;
import de.paladinsinn.rollenspielcons.persistence.time.EmbeddableTimeSpec;
import jakarta.annotation.Nullable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@Entity(name = "Event")
@Table(schema = "cons", name = "EVENTS")
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaEvent extends AbstractBaseEntity implements Event {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Embedded
  @NotNull(message = "The temporal data must be set.")
  private EmbeddableTimeSpec temporalData;
  
  @Column(name = "EXTERNAL_ID", length = 100)
  @Nullable
  @Size(min = 1, max = 100, message = "The Google Calendar ID must be between {min} and {max} characters long.")
  private String externalId;
  public Optional<String> getExternalId() {
    return Optional.ofNullable(externalId);
  }
  
  @OneToMany(
      targetEntity = JpaPhysicalAddress.class,
      mappedBy = "event",
      orphanRemoval = true,
      fetch = EAGER,
      cascade = {PERSIST, MERGE, DETACH, REFRESH, REMOVE}
  )
  @NotNull(message = "At least one location must be set")
  @Size(min = 1, max = 10, message = "A single convention can have between 1 and 10 locations")
  @Builder.Default
  private Set<PhysicalAddress> locations = new HashSet<>(10);
  
  @OneToMany(
      targetEntity = JpaWebLocation.class,
      mappedBy = "event",
      orphanRemoval = true,
      fetch = EAGER,
      cascade = {PERSIST, MERGE, DETACH, REFRESH, REMOVE}
  )
  @NotNull(message = "At least one location must be set")
  @Size(min = 1, max = 10, message = "A single convention can have between 1 and 10 locations")
  @Builder.Default
  private Set<WebLocation> webLocations = new HashSet<>(10);

  @OneToOne(
      orphanRemoval = true,
      fetch = EAGER,
      cascade = {PERSIST, MERGE, DETACH, REFRESH, REMOVE}
  )
  @Nullable
  private JpaWebLocation website;
  
  @Column(length = 4000)
  @NotNull(message = "The description must be set.")
  @Size(min = 1, max = 4000, message = "The description must be between {min} and {max} characters long.")
  private String description;
  
  @ElementCollection
  @CollectionTable(name = "EVENT_LABELS")
  @Column(name = "LABEL", nullable = false)
  @Nullable
  @Size(max = 100, message = "A single event can have at most 100 labels")
  @Builder.Default
  private Set<String> labels = new HashSet<>();
  
  @Transient
  @Override
  public String monitoredData() {
    return toString();
  }
}
