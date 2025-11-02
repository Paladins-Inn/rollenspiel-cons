package de.paladinsinn.rollenspielcons.persistence.events;

import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.model.events.EventImpl;
import de.paladinsinn.rollenspielcons.persistence.PersistedDisplayableName;
import de.paladinsinn.rollenspielcons.persistence.locations.LocationMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.DisplayTextMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.ExternalIdMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.OwnerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for JPA Events and the domain Events.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        LocationMapper.class,
        OwnerMapper.class,
        DisplayTextMapper.class,
        ExternalIdMapper.class
    }
)
public interface EventMapper {
    EventImpl toEventImpl(JpaEvent entity);
    
    @Mapping(target = "owner", source = "domain.owner")
    @Mapping(target = "name", expression = "java(toDisplayableName(domain))")
    @Mapping(target = "externalId", source = "externalId")
    @Mapping(target = "description", source = "domain.description")
    @Mapping(target = "labels", source = "domain.labels")
    JpaEvent toJpaEvent(Event domain);
    
    default PersistedDisplayableName toDisplayableName(final Event domain) {
      return PersistedDisplayableName.builder()
          .name(domain.getDisplayText())
          .uri(domain.getUri())
          .build();
    }
}