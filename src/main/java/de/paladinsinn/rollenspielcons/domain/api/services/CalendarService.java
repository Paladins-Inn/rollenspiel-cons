package de.paladinsinn.rollenspielcons.domain.api.services;


import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-05
 */
public interface CalendarService {
  List<Event> getEvents(@NotNull final Calendar calendar) throws CalendarException;
  List<Event> getEvents(@NotNull final Calendar calendar, @NotNull final LocalDate from) throws CalendarException;
  List<Event> getEvents(@NotNull final Calendar calendar, @NotNull final LocalDate from, final LocalDate till) throws CalendarException;
}
