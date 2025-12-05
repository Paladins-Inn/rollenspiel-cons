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

package de.paladinsinn.rollenspielcons.persistence.mapper;


import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.DisplayableName;
import de.paladinsinn.rollenspielcons.persistence.PersistedDisplayableName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Mapper(componentModel = "spring")
public interface DisplayTextMapper {
  DisplayableName toDisplayableName(PersistedDisplayableName entity);
  PersistedDisplayableName toPersistedDisplayableName(DisplayableName domain);
  
  @Mapping(target = "uri", source = "domain.uri")
  @Mapping(target = "name", source = "domain.displayText")
  PersistedDisplayableName toPersistedDisplayableName(HasDisplayText domain);
}
