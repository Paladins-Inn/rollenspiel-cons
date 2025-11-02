package de.paladinsinn.rollenspielcons.domain.api.calendars;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception while handling the persistence of a calendar.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class CalendarPersistenceException extends CalendarException {
  public CalendarPersistenceException(@NotNull final Calendar calendar, final String message) {
    super(calendar, message);
  }

  public CalendarPersistenceException(@NotNull final Calendar calendar, final String message, final Throwable cause) {
    super(calendar, message, cause);
  }
  
  public CalendarPersistenceException(@NotNull final Calendar calendar) {
    super(calendar, "Could not persist calendar " + calendar);
  }

  public CalendarPersistenceException(@NotNull final Calendar calendar, final Throwable cause) {
    super(calendar, "Could not persist calendar " + calendar, cause);
  }
}
