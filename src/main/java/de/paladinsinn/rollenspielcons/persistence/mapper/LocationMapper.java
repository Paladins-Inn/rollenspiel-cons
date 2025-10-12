package de.paladinsinn.rollenspielcons.persistence.mapper;

import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.persistence.locations.JpaPhysicalAddress;
import de.paladinsinn.rollenspielcons.persistence.locations.JpaWebLocation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    PhysicalAddress toPhysicalAddress(JpaPhysicalAddress entity);
    JpaPhysicalAddress toJpaPhysicalAddress(PhysicalAddress domain);

    WebLocation toWebLocation(JpaWebLocation entity);
    JpaWebLocation toJpaWebLocation(WebLocation domain);
}