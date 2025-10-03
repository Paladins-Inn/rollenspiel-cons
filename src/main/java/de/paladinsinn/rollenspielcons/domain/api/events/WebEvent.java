package de.paladinsinn.rollenspielcons.domain.api.events;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.domain.model.events.WebEventImpl;
import de.paladinsinn.rollenspielcons.domain.api.time.Timed;
import java.util.Set;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = WebEventImpl.class)
public interface WebEvent extends HasId, HasDisplayText, Event, Timed {
  /**
   * Locations of the event.
   *
   * @return set of locations
   */
  Set<WebLocation> getLocations();
}
