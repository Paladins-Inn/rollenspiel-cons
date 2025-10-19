package de.paladinsinn.rollenspielcons.persistence.calendars;


import de.paladinsinn.rollenspielcons.persistence.importers.JpaRefreshTokenAuthentication;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-19
 */
@Entity(name = "GoogleCalendar")
@DiscriminatorValue("GCAL")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class JpaGoogleCalendar extends AbstractJpaCalendar {
  
  @Embedded
  private JpaRefreshTokenAuthentication authentication;
}
