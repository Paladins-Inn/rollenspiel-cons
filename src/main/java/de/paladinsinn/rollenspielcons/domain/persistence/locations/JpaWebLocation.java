package de.paladinsinn.rollenspielcons.domain.persistence.locations;


import de.paladinsinn.rollenspielcons.domain.api.events.WebEvent;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.domain.persistence.events.JpaWebEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Entity(name = "WebLocation")
@Table(name = "WEBLOCATIONS")
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaWebLocation extends JpaLocation implements WebLocation {
  @ManyToOne(targetEntity = JpaWebEvent.class, optional = false)
  @NotBlank(message = "The event must be set.")
  private WebEvent event;
}
