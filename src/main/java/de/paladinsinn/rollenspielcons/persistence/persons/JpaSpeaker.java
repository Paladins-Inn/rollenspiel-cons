package de.paladinsinn.rollenspielcons.persistence.persons;


import de.paladinsinn.rollenspielcons.domain.api.persons.Speaker;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * Speaker and/or Gamemaster at a session.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-04
 */
@Entity(name = "Speaker")
@DiscriminatorValue("SPEAKER")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaSpeaker extends JpaPerson implements Speaker {
}
