/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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