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

package de.paladinsinn.rollenspielcons.persistence.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * A full location for all used systems.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Entity(name = "PhysicalAddress")
@Table(schema = "cons", name = "PHYSICALADDRESSES")
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, of = {"threeWords", "longitude", "latitude"})
@EqualsAndHashCode(callSuper = true, of = {"longitude", "latitude"})
public class JpaPhysicalAddress extends JpaLocation implements PhysicalAddress {
  @Column(name = "ADDRESS", nullable = false, length = 250)
  @NotBlank(message = "Address must be set")
  @Size(min = 5, max = 250, message = "Address must be between 5 and 250 characters long")
  private String address;
  
  @Column(name = "THREE_WORDS", nullable = false, length = 100)
  @NotBlank(message = "threeWords must be set")
  @Pattern(regexp = "^[a-zA-ZäöüÄÖÜß]+\\.[a-zA-ZäöüÄÖÜß]+\\.[a-zA-ZäöüÄÖÜß]+$", message = "threeWords must be in the format word1.word2.word3 with only letters and German umlauts")
  private String threeWords;
  
  @Column(name = "LONGITUDE", nullable = false)
  @NotNull(message = "Longitude must be set")
  @Size(min = -180, max = 180, message = "Longitude must be between {min} and {max}")
  private Double longitude;
  
  @Column(name = "LATITUDE", nullable = false)
  @NotNull(message = "Latitude must be set")
  @Size(min = -90, max = 90, message = "Latitude must be between {min} and {max}")
  private Double latitude;
  
  
  @Override
  @Transient
  public String getDisplayText() {
    return address;
  }
  
  @Override
  @Transient
  public String getEmailText() {
    return address + " (" + getUri() + ")";
  }
  
  @Override
  @Transient
  public String getUri() {
    return "https://www.openstreetmap.org/#map=19/" + longitude + "/" + latitude;
  }
}
