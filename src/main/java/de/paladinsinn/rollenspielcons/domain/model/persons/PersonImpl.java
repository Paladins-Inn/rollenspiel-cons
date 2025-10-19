package de.paladinsinn.rollenspielcons.domain.model.persons;


import de.paladinsinn.rollenspielcons.domain.api.persons.Person;
import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-04
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class PersonImpl extends AbstractModelBase implements Person {
  private String owner;
}
