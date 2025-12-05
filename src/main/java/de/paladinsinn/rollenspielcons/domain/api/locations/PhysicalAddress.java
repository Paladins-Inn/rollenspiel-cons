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

package de.paladinsinn.rollenspielcons.domain.api.locations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.model.locations.PhysicalAddressImpl;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = PhysicalAddressImpl.class)
public interface PhysicalAddress extends Location {
  /**
   * Full address of the location.
   *
   * @return full address
   */
  String getAddress();
  
  /**
   * The what3words address of the location.
   *
   * @return what3words address
   */
  String getThreeWords();
  
  /**
   * Longitude of the location.
   *
   * @return longitude
   */
  Double getLongitude();
  
  /**
   * Latitude of the location.
   *
   * @return latitude
   */
  Double getLatitude();
}
