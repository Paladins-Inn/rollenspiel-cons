package de.paladinsinn.rollenspielcons.domain.api.calendars;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception while importing an external calendar.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class CalendarImportException extends CalendarException {
  public CalendarImportException(@NotNull final Calendar calendar, final String message) {
    super(calendar, message);
  }

  public CalendarImportException(@NotNull final Calendar calendar, final String message, final Throwable cause) {
    super(calendar, message, cause);
  }

  public CalendarImportException(@NotNull final Calendar calendar, final Throwable cause) {
    super(calendar, cause.getMessage(), cause);
  }
}
