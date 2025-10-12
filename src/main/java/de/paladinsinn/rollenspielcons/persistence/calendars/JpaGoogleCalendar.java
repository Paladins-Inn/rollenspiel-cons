package de.paladinsinn.rollenspielcons.persistence.calendars;


import de.paladinsinn.rollenspielcons.persistence.importers.JpaGoogleImporterAuthentication;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static de.paladinsinn.rollenspielcons.domain.api.SystemConstants.URL_MAX_LENGTH;
import static de.paladinsinn.rollenspielcons.domain.api.SystemConstants.URL_MIN_LENGTH;


/**
 * The google calendar implementation of the calendar import information entity.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Entity(name = "GoogleCalendar")
@Table(name = "GOOGLE_CALENDARS")
@DiscriminatorValue("GCAL") // Has To match the value in {@link CalendarType}
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class JpaGoogleCalendar extends AbstractJpaCalendar {
  @Embedded
  private JpaGoogleImporterAuthentication authentication;
  
  @Column(name = "CALENDAR_ID", nullable = false)
  @Size(min = URL_MIN_LENGTH, max = URL_MAX_LENGTH, message = "The calendar ID must be between {min} and {max} characters long.")
  private String calendarId;
}
