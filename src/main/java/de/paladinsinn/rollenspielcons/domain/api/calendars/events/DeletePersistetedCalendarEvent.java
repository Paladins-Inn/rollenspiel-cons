package de.paladinsinn.rollenspielcons.domain.api.calendars.events;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * This event will delete an existing calendar entry
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeletePersistetedCalendarEvent extends AbstractCalendarBaseEvent {
}
