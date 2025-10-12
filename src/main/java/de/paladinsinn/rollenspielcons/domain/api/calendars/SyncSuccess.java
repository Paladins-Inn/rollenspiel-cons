package de.paladinsinn.rollenspielcons.domain.api.calendars;

import io.swagger.v3.oas.annotations.media.Schema;
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
