package de.paladinsinn.rollenspielcons.persistence.calendars;

import de.paladinsinn.rollenspielcons.domain.model.calendars.ICalCalendar;
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
  ICalCalendar toDomain(AbstractJpaCalendar jpa);
  
  ICalCalendar toDomain(JpaCalDavCalendar jpa);
  
  JpaCalDavCalendar toJpa(ICalCalendar domain);
}