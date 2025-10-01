package de.paladinsinn.rollenspielcons.domain.api.iam;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.model.iam.GroupImpl;
import jakarta.validation.constraints.NotNull;

/**
 * A group of identities.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@JsonDeserialize(as = GroupImpl.class)
public interface Group extends HasId, HasDisplayText, HasOwner {
  /**
   * Checks if the given identity is a member of this group.
   *
   * @param identity the identity to check
   * @return true if the identity is a member of this group, false otherwise
   */
  boolean isMember(@NotNull Identity identity);
  
  Owner getOwner();
}
