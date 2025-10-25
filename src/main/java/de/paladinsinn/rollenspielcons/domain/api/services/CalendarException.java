package de.paladinsinn.rollenspielcons.domain.api.services;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
public class CalendarException extends Exception {
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
