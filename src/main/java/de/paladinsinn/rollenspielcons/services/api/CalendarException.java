package de.paladinsinn.rollenspielcons.services.api;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
public class CalendarException extends Exception {
  @Serial
  private static final long serialVersionUID = 1L;

  public CalendarException() {
    super("Developer has been too lazy to provide a proper message.");
  }

  public CalendarException(final String message) {
    super(message);
  }

  public CalendarException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CalendarException(final Throwable cause) {
    super(cause.getMessage(), cause);
  }
}
