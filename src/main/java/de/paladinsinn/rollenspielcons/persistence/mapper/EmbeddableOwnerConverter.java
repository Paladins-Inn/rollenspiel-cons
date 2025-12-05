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


import de.paladinsinn.rollenspielcons.persistence.EmbeddableOwner;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-02
 */
@Converter(autoApply = true)
@Slf4j
public class EmbeddableOwnerConverter implements AttributeConverter<EmbeddableOwner, String> {

  @Override
  public String convertToDatabaseColumn(EmbeddableOwner attribute) {
    log.trace("enter - {}", attribute);
    
    String result = attribute == null ? null : attribute.getOwner();
    
    log.trace("exit - {}", result);
    return result;
  }

  @Override
  public EmbeddableOwner convertToEntityAttribute(String dbData) {
    log.trace("enter - {}", dbData);
    
    EmbeddableOwner result = dbData == null ? null : EmbeddableOwner.builder().owner(dbData).build();
    
    log.trace("exit - {}", result);
    return result;
  }
}