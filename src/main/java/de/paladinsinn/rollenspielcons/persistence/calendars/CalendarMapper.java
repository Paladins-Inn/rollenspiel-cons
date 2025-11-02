package de.paladinsinn.rollenspielcons.persistence.calendars;

import de.paladinsinn.rollenspielcons.domain.model.calendars.IcalCalendar;
import de.paladinsinn.rollenspielcons.persistence.importers.ImportAuthenticationMapper;
import de.paladinsinn.rollenspielcons.persistence.mapper.OwnerMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for JPA Events and the domain Events.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        OwnerMapper.class,
        ImportAuthenticationMapper.class
    }
)
public interface CalendarMapper {
  IcalCalendar toDomain(AbstractJpaCalendar jpa);
  
  IcalCalendar toDomain(JpaCalDavCalendar jpa);
  
  JpaCalDavCalendar toJpa(IcalCalendar domain);
}