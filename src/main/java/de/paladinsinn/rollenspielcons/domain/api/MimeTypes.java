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

package de.paladinsinn.rollenspielcons.domain.api;


/**
 * Mime Types used in this application.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@SuppressWarnings("unused")
public interface MimeTypes {
  String APPLICATION_JSON_1 = "application/vnd.1.3.6.1.4.1.33132.v1+json";
  String APPLICATION_JSON_2 = "application/vnd.1.3.6.1.4.1.33132.v2+json";

  String APPLICATION_YAML_1 = "application/vnd.1.3.6.1.4.1.33132.v1+yaml";
  String APPLICATION_YAML_2 = "application/vnd.1.3.6.1.4.1.33132.v2+yaml";

  String APPLICATION_CSV_1 = "application/vnd.1.3.6.1.4.1.33132.v1+csv";
  String APPLICATION_CSV_2 = "application/vnd.1.3.6.1.4.1.33132.v2+csv";
}
