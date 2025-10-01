package de.paladinsinn.rollenspielcons.domain.api.iam;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
public interface HasIdentity {
  /**
   * Returns the identity of this entity.
   *
   * @return the identity of this entity
   */
  Identity getIdentity();
}
