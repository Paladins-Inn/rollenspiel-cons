package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Identity extends AbstractBaseEntity implements Owner, HasId, HasDisplayText, HasIdentity {
  /**
   * The OIDC users that are linked to this identity.
   */
  @NotNull(message = "The OIDC users must not be null.")
  @Size(max = 10, message = "The title must be between {min} and {max} characters long.")
  @Builder.Default
  @ToString.Exclude
  private Set<OidcUser> oidcUsers = new HashSet<>(10);
  
  
  /**
   * The roles that are assigned to this identity.
   */
  @NotNull(message = "The roles must not be null.")
  @Builder.Default
  @ToString.Exclude
  private Set<Role> roles = new HashSet<>();
  
  
  /**
   * The groups that are assigned to this identity.
   */
  @NotNull(message = "The owners must not be null.")
  @Builder.Default
  @ToString.Exclude
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
