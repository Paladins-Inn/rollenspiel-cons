package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Group;
import de.paladinsinn.rollenspielcons.domain.api.iam.Identity;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.XSlf4j;

import static jakarta.persistence.FetchType.EAGER;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Entity(name = "Group")
@Table(name = "GROUPS")
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true, includeFieldNames = true)
@XSlf4j
public class PersistentGroup extends PersistentOwner implements Group {
  @ManyToOne(fetch = EAGER, optional = true, targetEntity = PersistentOwner.class)
  @JoinColumn(name = "OWNER_ID", nullable = false)
  @NotNull
  private Owner owner;
  
  @Override
  public boolean isMember(@NotNull final Identity identity) {
    log.entry(identity);
    
    return log.exit(identity.isInGroup(this));
  }
  
  @Override
  public boolean isOwner(@NotNull final Identity identity) {
    log.entry(identity);
    
    boolean result = false;
    
    if (Group.class.isAssignableFrom(owner.getClass())) {
      result = ((Group) owner).isMember(identity);
    } else if (Identity.class.isAssignableFrom(owner.getClass())) {
      result = owner.getId() == identity.getId();
    }
    
    return log.exit(result);
  }
}
