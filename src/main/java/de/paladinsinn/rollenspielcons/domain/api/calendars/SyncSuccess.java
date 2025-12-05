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

package de.paladinsinn.rollenspielcons.domain.api.calendars;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The status of calendar syncs.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Schema(
    description = "Result of synchronization attempts.",
    examples = {"NEVER", "OK"},
    oneOf = {SyncSuccess.class},
    defaultValue = "NEVER"
)
@RequiredArgsConstructor
public enum SyncSuccess {
  NEVER(0),
  OK(200),
  CONVERSION_FAILED(501),
  SERVER_ERROR(503),
  ;
  
  @Getter
  private final int code;
}
