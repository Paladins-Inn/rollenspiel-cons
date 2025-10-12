package de.paladinsinn.rollenspielcons.persistence.mapper;

import de.paladinsinn.rollenspielcons.domain.api.time.TimeSpec;
import de.paladinsinn.rollenspielcons.persistence.time.EmbeddableTimeSpec;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimeSpecMapper {
    TimeSpec toTimeSpec(EmbeddableTimeSpec entity);
    EmbeddableTimeSpec toEmbeddableTimeSpec(TimeSpec domain);
}