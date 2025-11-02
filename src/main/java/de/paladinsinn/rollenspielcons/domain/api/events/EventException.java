package de.paladinsinn.rollenspielcons.domain.api.events;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

/**
 * An exception concerning a event.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class EventException extends Exception {
  private final Event event;
  
  public EventException(@NotNull final Event event, final String message) {
    super(message);
    
    this.event = event;
  }

  public EventException(@NotNull final Event event, final String message, final Throwable cause) {
    super(message, cause);
    
    this.event = event;
  }

  public EventException(@NotNull final Event event, final Throwable cause) {
    super(cause.getMessage(), cause);
    
    this.event = event;
  }
}
