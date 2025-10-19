package de.paladinsinn.rollenspielcons.persistence.persons;


import de.paladinsinn.rollenspielcons.domain.api.persons.Person;
import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;


/**
 * Speaker and/or Gamemaster at a session.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-04
 */
@Entity(name = "Person")
@Table(schema = "cons", name = "PERSONS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "PERSON_TYPE", length = 15)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JpaPerson extends AbstractBaseEntity implements Person {
}
