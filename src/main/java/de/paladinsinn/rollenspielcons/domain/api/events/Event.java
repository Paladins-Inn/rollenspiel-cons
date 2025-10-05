package de.paladinsinn.rollenspielcons.domain.api.events;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasEtag;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.time.Timed;
import de.paladinsinn.rollenspielcons.domain.model.events.BaseEvent;
import java.util.Optional;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = BaseEvent.class)
public interface Event extends HasId, HasDisplayText, HasOwner, Timed, HasEtag {
  /**
   * Returns the Google Calendar event ID if this event is synchronized with Google Calendar.
   *
   * @return the Google Calendar event ID or null if not synchronized
   */
  Optional<String> getGoogleId();
}
