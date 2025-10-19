package de.paladinsinn.rollenspielcons.domain.api.persons;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.HasVersion;
import de.paladinsinn.rollenspielcons.domain.model.persons.SpeakerImpl;
import java.io.Serializable;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-04
 */
@JsonDeserialize(as = SpeakerImpl.class)
public interface Person extends HasId, HasVersion, HasDisplayText, HasOwner, Serializable {
}
