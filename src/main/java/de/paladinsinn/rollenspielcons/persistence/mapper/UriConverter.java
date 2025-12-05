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

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.NotNull;
import java.net.URI;

@Converter(autoApply = true)
public class UriConverter implements AttributeConverter<URI, String> {
    @Override
    public String convertToDatabaseColumn(@NotNull final URI attribute) {
        return attribute == null ? null : attribute.toString();
    }

    @Override
    public URI convertToEntityAttribute(@NotNull final String dbData) {
        return dbData == null ? null : URI.create(dbData);
    }
}