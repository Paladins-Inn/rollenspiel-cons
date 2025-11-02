package de.paladinsinn.rollenspielcons.domain.api.calendars;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Schema(
    description = "The type of calendar",
    examples = {"CALDAV", "GCAL"},
    enumeration = {"CALDAV", "GCAL"}
)
public enum CalendarType {
  /** iCAL via CALDAV as provided by Nextcloud. */
  CALDAV,
  
  /** Google Calendar via the Google API for Calendars. */
  GCAL
}
