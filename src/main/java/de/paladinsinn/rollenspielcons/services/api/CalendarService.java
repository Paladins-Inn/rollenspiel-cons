package de.paladinsinn.rollenspielcons.services.api;


import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
public interface CalendarService {
  List<Event> getEvents(@NotBlank final String calendar) throws CalendarException;
  List<Event> getEvents(@NotBlank final String calendar, @notNull final LocalDate from) throws CalendarException;
  List<Event> getEvents(@NotBlank final String calendar, @notNull final LocalDate from, final LocalDate till) throws CalendarException;
}
