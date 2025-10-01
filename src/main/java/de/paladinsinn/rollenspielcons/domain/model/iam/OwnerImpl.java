package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-02
 */
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class OwnerImpl extends AbstractModelBase implements Owner {
}
