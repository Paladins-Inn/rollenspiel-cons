package de.paladinsinn.rollenspielcons.domain.api.iam;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.model.iam.RoleImpl;
import java.util.Set;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@JsonDeserialize(as = RoleImpl.class)
public interface Role extends HasId, HasDisplayText, HasOwner {
  /**
   * The permissions that are assigned to this role.
   *
   * @return the permissions that are assigned to this role
   */
  Set<Permission> getPermissions();
}
