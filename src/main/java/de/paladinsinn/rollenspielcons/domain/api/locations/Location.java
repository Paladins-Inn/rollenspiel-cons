package de.paladinsinn.rollenspielcons.domain.api.locations;


import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.HasVersion;
import java.io.Serializable;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
public interface Location extends HasId, HasVersion, HasDisplayText, HasOwner, Serializable {
}
