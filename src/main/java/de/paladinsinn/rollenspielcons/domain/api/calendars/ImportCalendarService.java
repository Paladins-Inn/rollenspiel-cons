package de.paladinsinn.rollenspielcons.domain.api.calendars;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-09
 */
public interface ImportCalendarService {
  void importCalendar(Calendar calendar) throws CalendarException;
}
