package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode(callSuper = true)
public class Owner extends AbstractBaseEntity implements HasId, HasDisplayText {
  /**
   * The identities that are consigned to this owner.
   */
  @Builder.Default
  private Set<Identity> identities = new HashSet<>();
  
  /**
   * The groups that are consigned to this owner.
   */
  @Builder.Default
  private Set<Group> groups = new HashSet<>();
  
  /**
   * Checks if the given identity is an owner, either directly or via a group membership.
   *
   * @param identity the identity to check
   * @return true if the identity is an owner, false otherwise
   */
  public boolean isOwner(@NotNull Identity identity) {
    return identities.contains(identity) || groups.stream().anyMatch(g -> g.isMember(identity));
  }
}
