package de.paladinsinn.rollenspielcons.domain.api.events;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasEtag;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.HasVersion;
import de.paladinsinn.rollenspielcons.domain.api.integrations.IsImportable;
import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.domain.api.time.Timed;
import de.paladinsinn.rollenspielcons.domain.model.events.EventImpl;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = EventImpl.class)
public interface Event extends HasId, HasVersion, HasDisplayText, HasOwner, Timed, HasEtag, IsImportable {
  
  /**
   * @return The description of the event.
   */
  @Size(min = 1, max = 4000, message = "The description must be between {min} and {max} characters long.")
  String getDescription();
  
  /**
   * @return The website of the event.
   */
  @NotNull(message = "The website must be set.")
  WebLocation getWebsite();
  
  /**
   * @return The physical locations of the event.
   */
  Set<PhysicalAddress> getLocations();
  
  /**
   * @return The web sites of the event.
   */
  Set<WebLocation> getWebLocations();
  
  default boolean isPhysical() {
    return !getLocations().isEmpty();
  }
  
  /**
   * @return The labels of the event.
   */
  @NotNull(message = "The labels set must be valid.")
  @Size(max = 100, message = "The labels set must not exceed {max} elements.")
  Set<String> getLabels();
}
