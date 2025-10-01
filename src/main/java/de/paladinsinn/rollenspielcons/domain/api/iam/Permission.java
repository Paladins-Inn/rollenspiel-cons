package de.paladinsinn.rollenspielcons.domain.api.iam;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.model.iam.PermissionImpl;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * The interface for permission handling.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@JsonDeserialize(as = PermissionImpl.class)
public interface Permission extends HasId, HasDisplayText {
  
  Action getAction();
  
  String getObject();
  
  Permission.Ruling getRuling();
  
  
  /**
   * Applies this rule to the given identity, action and object.
   * <p>
   * The rule applies if the role of the rule is assigned to the identity, the action matches and
   * the object matches.
   * </p>
   *
   * @param identity the identity to check
   * @param action   the action to check
   * @param object   the object to check
   * @return ALLOW if the rule applies, DENY otherwise
   */
  default boolean apply(
      @NotNull final Identity identity,
      @NotNull final Action action,
      @NotNull final HasOwner object
  ) {
    return matchRule(identity, action, object) && getRuling().isRuling();
  }
  
  default boolean matchRule(final Identity identity, final Action action, final HasOwner object) {
    return applyObjectAction(action, object) && applyOwner(identity, object);
  }
  
  default boolean applyObjectAction(@NotNull final Action action, @NotNull final HasOwner object) {
    return this.getAction().equals(action) && getObject().equals(object.getClass().getCanonicalName());
  }
  
  default boolean applyOwner(@NotNull final Identity identity, @NotNull final HasOwner object) {
    return object.isOwnedBy(identity);
  }
  
  
  /**
   * The action the rule applies to.
   */
  enum Ruling {
    ALLOW(true),
    DENY(false);
    
    Ruling(final boolean ruling) {
      this.ruling = ruling;
    }
    
    @Getter
    private final boolean ruling;
  }
}
