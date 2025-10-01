package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Group;
import de.paladinsinn.rollenspielcons.domain.api.iam.Identity;
import de.paladinsinn.rollenspielcons.domain.api.iam.OidcUser;
import de.paladinsinn.rollenspielcons.domain.api.iam.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.FetchType.EAGER;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Entity(name = "Identity")
@Table(name = "IDENTITIES")
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PersistentIdentity extends PersistentOwner implements Identity {
  @OneToMany(fetch = EAGER, cascade = ALL, orphanRemoval = true, targetEntity = PersistentOidcUser.class)
  @Builder.Default
  private Set<OidcUser> oidcUsers = new HashSet<>();
  
  @ManyToMany(
      fetch = EAGER,
      cascade = {PERSIST, DETACH, MERGE, REFRESH},
      targetEntity = PersistentRole.class
  )
  @JoinTable(
      name = "IDENTITY_ROLE",
      joinColumns = @JoinColumn(name = "IDENTITY_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
  )
  @Builder.Default
  private Set<Role> roles = new HashSet<>();
  
  @ManyToMany(
      fetch = EAGER,
      cascade = {PERSIST, DETACH, MERGE, REFRESH},
      targetEntity = PersistentGroup.class
  )
  @JoinTable(
      name = "IDENTITY_GROUP",
      joinColumns = @JoinColumn(name = "IDENTITY_ID"),
      inverseJoinColumns = @JoinColumn(name = "GROUP_ID")
  )
  @Builder.Default
  private Set<Group> groups = new HashSet<>();
  
  /**
   * Checks if this identity has the given role.
   *
   * @param role the role to check
   * @return {@code true} if this identity has the given role, {@code false} otherwise
   */
  public boolean hasRole(@NotNull Role role) {
    return roles.contains(role);
  }
  
  
  /**
   * Checks if this identity is in the given group.
   *
   * @param group the group to check
   * @return {@code true} if this identity is in the given group, {@code false} otherwise
   */
  public boolean isInGroup(@NotNull Group group) {
    return groups.contains(group);
  }
  
  @Override
  public Identity getIdentity() {
    return this;
  }
  
  @Override
  public boolean isOwner(final Identity identity) {
    return equals(identity);
  }
}
