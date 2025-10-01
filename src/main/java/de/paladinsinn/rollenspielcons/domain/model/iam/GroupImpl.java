package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Group;
import de.paladinsinn.rollenspielcons.domain.api.iam.Identity;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@SuperBuilder(toBuilder = true)
@Getter
@Setter(value = AccessLevel.PACKAGE, onMethod_ = @__(@Deprecated)) // Only for testing
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class GroupImpl extends OwnerImpl implements Group {
  @NotNull
  private Owner owner;
  
  @Override
  public boolean isMember(@NotNull Identity identity) {
    return identity.isInGroup(this);
  }
  
  @Override
  public boolean isOwner(final Identity identity) {
    return identity.isInGroup(this);
  }
}
