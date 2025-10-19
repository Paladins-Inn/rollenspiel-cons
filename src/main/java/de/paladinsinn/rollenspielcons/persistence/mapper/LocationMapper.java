package de.paladinsinn.rollenspielcons.persistence.mapper;

import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.domain.model.locations.PhysicalAddressImpl;
import de.paladinsinn.rollenspielcons.domain.model.locations.WebLocationImpl;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEvent;
import de.paladinsinn.rollenspielcons.persistence.locations.JpaPhysicalAddress;
import de.paladinsinn.rollenspielcons.persistence.locations.JpaWebLocation;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        OwnerMapper.class,
        DisplayTextMapper.class
    }
)
public interface LocationMapper {
  PhysicalAddressImpl toPhysicalAddress(JpaPhysicalAddress entity);
  @Mapping(target = "id", source = "domain.id")
  @Mapping(target = "version", source = "domain.version")
  @Mapping(target = "owner", source = "domain.owner")
  @Mapping(target = "name", source = "domain")
  @Mapping(target = "event", expression = "java(event)")
  JpaPhysicalAddress toJpaPhysicalAddress(PhysicalAddress domain, @Context JpaEvent event);
  
  WebLocationImpl toWebLocation(JpaWebLocation entity);
  @Mapping(target = "id", source = "domain.id")
  @Mapping(target = "version", source = "domain.version")
  @Mapping(target = "owner", source = "domain.owner")
  @Mapping(target = "name", source = "domain")
  @Mapping(target = "event", expression = "java(event)")
  JpaWebLocation toJpaWebLocation(WebLocation domain, @Context JpaEvent event);
}