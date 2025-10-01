package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.XSlf4j;


/**
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@SuperBuilder(toBuilder = true)
@Getter
@Setter(value = AccessLevel.PACKAGE, onMethod_ = @__(@Deprecated)) // Only for testing
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@XSlf4j
public class Rule extends AbstractBaseEntity implements HasId, HasDisplayText {
  /**
   * The action that is protected. Defaults to {@link Action#READ} when not set.
   */
  @NotNull(message = "The action must not be null.")
  @Builder.Default
  private Action action = Action.READ;
  
  /**
   * The object the rule applies to.
   * The object is normally the {@link Class#getCanonicalName()} of a resource class.
   */
  @NotNull(message = "The object must not be null.")
  @Size(min = 1, max = 255, message = "The object must be between {min} and {max} characters long.")
  private String object;
  
  /**
   * The ruling of this access rule. Defaults to {@link Ruling#ALLOW} when not set.
   */
  @NotNull(message = "Th ruling must be set.")
  @Builder.Default
  private Ruling ruling = Ruling.ALLOW;

  
  /**
   * Applies this rule to the given identity, action and object.
   * <p>
   * The rule applies if the role of the rule is assigned to the identity, the action matches and the object matches.
   * </p>
   *
   * @param identity the identity to check
   * @param action   the action to check
   * @param object   the object to check
   * @return ALLOW if the rule applies, DENY otherwise
   */
  public boolean apply(
      @NotNull final Identity identity,
      @NotNull final Action action,
      @NotNull final HasOwner object
  ) {
    log.entry(identity, action, object, this);
    
    boolean result =
        matchRule(identity, action, object)
        && ruling.isRuling();
    
    return log.exit(result);
  }
  
  private boolean matchRule(final Identity identity, final Action action, final HasOwner object) {
    return applyObjectAction(action, object) && applyOwner(identity, object);
  }
  
  private boolean applyObjectAction(@NotNull final Action action, @NotNull final HasOwner object) {
    return this.action.equals(action) && this.object.equals(object.getClass().getCanonicalName());
  }
  
  private boolean applyOwner(@NotNull final Identity identity, @NotNull final HasOwner object) {
    return object.isOwnedBy(identity);
  }
  
  
  /**
   * The action the rule applies to.
   */
  public enum Ruling {
    ALLOW(true),
    DENY(false);
    
    private Ruling(final boolean ruling) {
      this.ruling = ruling;
    }
    
    @Getter
    private boolean ruling;
  }
}
