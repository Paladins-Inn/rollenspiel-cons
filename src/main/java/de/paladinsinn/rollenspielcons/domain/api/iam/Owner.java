package de.paladinsinn.rollenspielcons.domain.api.iam;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.model.iam.OwnerImpl;
import jakarta.validation.constraints.NotNull;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@JsonDeserialize(as = OwnerImpl.class)
public interface Owner extends HasId, HasDisplayText {
  /**
   * Checks if the given identity is an owner, either directly or via a group membership.
   *
   * @param identity the identity to check
   * @return true if the identity is an owner, false otherwise
   */
  boolean isOwner(@NotNull Identity identity);
}
