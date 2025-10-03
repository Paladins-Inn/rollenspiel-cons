package de.paladinsinn.rollenspielcons.domain.api.locations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.model.locations.WebLocationImpl;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = WebLocationImpl.class)
public interface WebLocation extends Location {
}
