package de.paladinsinn.rollenspielcons.persistence.mapper;

import de.paladinsinn.rollenspielcons.domain.api.persons.Person;
import de.paladinsinn.rollenspielcons.domain.api.persons.Speaker;
import de.paladinsinn.rollenspielcons.domain.api.persons.Participant;
import de.paladinsinn.rollenspielcons.persistence.persons.JpaPerson;
import de.paladinsinn.rollenspielcons.persistence.persons.JpaSpeaker;
import de.paladinsinn.rollenspielcons.persistence.persons.JpaParticipant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toPerson(JpaPerson entity);
    JpaPerson toJpaPerson(Person domain);

    Speaker toSpeaker(JpaSpeaker entity);
    JpaSpeaker toJpaSpeaker(Speaker domain);

    Participant toParticipant(JpaParticipant entity);
    JpaParticipant toJpaParticipant(Participant domain);
}