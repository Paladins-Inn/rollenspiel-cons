package de.paladinsinn.rollenspielcons.persistence.mapper;

import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.api.events.PhysicalEvent;
import de.paladinsinn.rollenspielcons.domain.api.events.WebEvent;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEvent;
import de.paladinsinn.rollenspielcons.persistence.events.JpaPhysicalEvent;
import de.paladinsinn.rollenspielcons.persistence.events.JpaWebEvent;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for JPA Events and the domain Events.
 */
@Mapper(
    componentModel = "spring",
    uses = {LocationMapper.class}
)
public interface EventMapper {
    // JpaEvent <-> BaseEvent
    Event toBaseEvent(JpaEvent entity);
    JpaEvent toJpaEvent(Event domain);

    // JpaWebEvent <-> WebEventImpl
    @Mapping(target = "locations", source = "locations")
    WebEvent toWebEventImpl(JpaWebEvent entity);

    @InheritInverseConfiguration
    JpaWebEvent toJpaWebEvent(WebEvent domain);

    // JpaPhysicalEvent <-> PhysicalEventImpl
    @Mapping(target = "locations", source = "locations")
    PhysicalEvent toPhysicalEventImpl(JpaPhysicalEvent entity);

    @InheritInverseConfiguration
    JpaPhysicalEvent toJpaPhysicalEvent(PhysicalEvent domain);
}