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


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImportDefinition;
import de.paladinsinn.rollenspielcons.domain.api.integrations.IsImportable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static de.paladinsinn.rollenspielcons.domain.api.integrations.ImportDefinition.EMPTY;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractImportableModelBase extends AbstractModelBase implements IsImportable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @NotNull(message = "The import definition must be set.")
  @Builder.Default
  private ImportDefinition importDefinition = EMPTY;
  
  @Nullable
  private String externalId;

  
  @Override
  public Optional<String> getExternalId() {
    return Optional.ofNullable(externalId);
  }
}
