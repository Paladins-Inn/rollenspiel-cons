package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.XSlf4j;


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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XSlf4j
public class GroupAndIdentitesOwner extends AbstractBaseEntity implements Owner, HasId, HasDisplayText {
  /**
   * The identities that are consigned to this owner.
   */
  @Builder.Default
  @ToString.Exclude
  private Set<Identity> identities = new HashSet<>();
  
  /**
   * The groups that are consigned to this owner.
   */
  @Builder.Default
  @ToString.Exclude
  private Set<Group> groups = new HashSet<>();
  
  
  @Override
  public boolean isOwner(final Identity identity) {
    log.entry(identity);
    
    boolean result = identities.contains(identity)
           || groups.stream().anyMatch(g -> g.isMember(identity));
    
    return log.exit(result);
  }
}
