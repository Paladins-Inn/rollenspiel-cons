package de.paladinsinn.rollenspielcons.persistence.persons;

import de.paladinsinn.rollenspielcons.domain.api.persons.Participant;
import de.paladinsinn.rollenspielcons.domain.api.persons.Speaker;
import de.paladinsinn.rollenspielcons.domain.model.persons.ParticipantImpl;
import de.paladinsinn.rollenspielcons.domain.model.persons.SpeakerImpl;
import de.paladinsinn.rollenspielcons.persistence.mapper.DisplayTextMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.OwnerMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        DisplayTextMapper.class,
        OwnerMapper.class
    },
    builder = @Builder
)
public interface PersonMapper {
  SpeakerImpl toSpeaker(JpaSpeaker entity);
  @Mapping(target = "name", source = "domain")
  JpaSpeaker toJpaSpeaker(Speaker domain);

  ParticipantImpl toParticipant(JpaParticipant entity);
  @Mapping(target = "name", source = "domain")
  JpaParticipant toJpaParticipant(Participant domain);
}