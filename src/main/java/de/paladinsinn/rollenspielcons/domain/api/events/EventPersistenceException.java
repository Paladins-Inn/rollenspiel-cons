package de.paladinsinn.rollenspielcons.domain.api.events;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception while handling the persistence of an event.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class EventPersistenceException extends EventException {
  public EventPersistenceException(@NotNull final Event event, final String message) {
    super(event, message);
  }

  public EventPersistenceException(@NotNull final Event event, final String message, final Throwable cause) {
    super(event, message, cause);
  }

  public EventPersistenceException(@NotNull final Event event, final Throwable cause) {
    super(event, cause.getMessage(), cause);
  }
}
