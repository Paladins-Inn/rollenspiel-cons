package de.paladinsinn.rollenspielcons.domain.api.calendars.events;


import de.paladinsinn.rollenspielcons.domain.api.AbstractBaseEvent;
import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * The base class for all calendar events.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractCalendarBaseEvent extends AbstractBaseEvent {
  /** The calendar this event belongs to. */
  private final Calendar calendar;
}
