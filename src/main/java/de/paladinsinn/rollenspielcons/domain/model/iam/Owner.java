package de.paladinsinn.rollenspielcons.domain.model.iam;


import jakarta.validation.constraints.NotNull;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
public interface Owner {
  /**
   * Checks if the given identity is an owner, either directly or via a group membership.
   *
   * @param identity the identity to check
   * @return true if the identity is an owner, false otherwise
   */
  boolean isOwner(@NotNull Identity identity);
}
