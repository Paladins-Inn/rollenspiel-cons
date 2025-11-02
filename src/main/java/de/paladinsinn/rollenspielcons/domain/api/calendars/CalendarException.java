package de.paladinsinn.rollenspielcons.domain.api.calendars;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception concerning a calendar.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class CalendarException extends Exception {
  private final Calendar calendar;
  
  public CalendarException(@NotNull final Calendar calendar, final String message) {
    super(message);
    
    this.calendar = calendar;
  }

  public CalendarException(@NotNull final Calendar calendar, final String message, final Throwable cause) {
    super(message, cause);
    
    this.calendar = calendar;
  }

  public CalendarException(@NotNull final Calendar calendar, final Throwable cause) {
    super(cause.getMessage(), cause);
    
    this.calendar = calendar;
  }
}
