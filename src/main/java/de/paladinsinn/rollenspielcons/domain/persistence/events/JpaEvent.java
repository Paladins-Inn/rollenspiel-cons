package de.paladinsinn.rollenspielcons.domain.persistence.events;


import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.persistence.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.persistence.time.EmbeddableTimeSpec;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@Entity(name = "Event")
@Inheritance(strategy = TABLE_PER_CLASS)
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

  
  @Override
  public String monitoredData() {
    return toString();
  }
}
