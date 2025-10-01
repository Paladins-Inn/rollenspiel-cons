package de.paladinsinn.rollenspielcons.domain.api.iam;


import jakarta.validation.constraints.NotNull;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
public interface HasOwner {
  /**
   * Returns the owner of this entity.
   *
   * @return the owner of this entity
   */
  Owner getOwner();
  
  
  /**
   * Checks if the given identity is the owner of this entity.
   *
   * @param identityHolder the identity to check
   * @return true if the identity is the owner of this entity, false otherwise
   */
  default boolean isOwnedBy(@NotNull HasIdentity identityHolder) {
    return getOwner().isOwner(identityHolder.getIdentity());
  }
}
