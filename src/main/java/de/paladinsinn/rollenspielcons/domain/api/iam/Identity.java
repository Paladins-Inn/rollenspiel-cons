package de.paladinsinn.rollenspielcons.domain.api.iam;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.model.iam.IdentityImpl;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@JsonDeserialize(as = IdentityImpl.class)
public interface Identity extends HasId, HasDisplayText, Owner, HasIdentity {
  Set<OidcUser> getOidcUsers();
  Set<Group> getGroups();
  Set<Role> getRoles();
  
  
  /**
   * Checks if this identity is in the given group.
   *
   * @param group the group to check
   * @return {@code true} if this identity is in the given group, {@code false} otherwise
   */
  default boolean isInGroup(@NotNull Group group) {
    return getGroups().contains(group);
  }
  

  /**
   * Checks if this identity has the given role.
   *
   * @param role the role to check
   * @return {@code true} if this identity has the given role, {@code false} otherwise
   */
  default boolean hasRole(@NotNull Role role) {
    return getRoles().contains(role);
  }
}
