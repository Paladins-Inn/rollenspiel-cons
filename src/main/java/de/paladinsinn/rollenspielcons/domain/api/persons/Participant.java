package de.paladinsinn.rollenspielcons.domain.api.persons;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.model.persons.ParticipantImpl;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-04
 */
@JsonDeserialize(as = ParticipantImpl.class)
public interface Participant extends Person {
}
