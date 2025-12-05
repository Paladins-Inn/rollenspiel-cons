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

package de.paladinsinn.rollenspielcons.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasVersion;
import java.beans.Transient;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 * A base entity with an ID and display names.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public class AbstractModelBase implements HasId, HasVersion, HasDisplayText, Serializable {
  /**
   * The unique identifier of this entity.
   */
  private long id;
  
  /**
   * The dataset version of this entity.
   */
  private int version;
  
  /**
   * The name of this entity.
   */
  private DisplayableName name;
  
  
  @Override
  @JsonIgnore
  @Transient
  public String getDisplayText() {
    return name.getDisplayText();
  }
  
  @Override
  @JsonIgnore
  @Transient
  public String getUri() {
    return name.getUri();
  }
}
