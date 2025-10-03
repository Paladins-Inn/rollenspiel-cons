package de.paladinsinn.rollenspielcons.domain.api.events;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.model.events.PhysicalEventImpl;
import de.paladinsinn.rollenspielcons.domain.api.time.Timed;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = PhysicalEventImpl.class)
public interface PhysicalEvent extends HasId, HasDisplayText, Event, Timed {
  java.util.Set<PhysicalAddress> getLocations();
}
