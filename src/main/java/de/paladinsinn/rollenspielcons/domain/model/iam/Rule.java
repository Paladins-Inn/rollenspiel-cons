package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode(callSuper = true)
@XSlf4j
public class Rule extends AbstractBaseEntity implements HasId, HasDisplayText {
  @NotNull(message = "The role must not be null.")
  private Role role;
  
  @NotNull(message = "The action must not be null.")
  private Action action;
  
  /**
   * The object the rule applies to.
   * The object is normally the {@link Class#getCanonicalName()} of a resource class.
   */
  @NotNull(message = "The object must not be null.")
  @Size(min = 1, max = 255, message = "The object must be between {min} and {max} characters long.")
  private String object;

  
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
  public Ruling apply(
      @NotNull final Identity identity,
      @NotNull final Action action,
      @NotNull final HasOwner object
  ) {
    log.entry(identity, action, object);
    
    return log.exit(
        role.isOwnedBy(identity)
        && object.isOwnedBy(identity)
        && this.action.equals(action)
        && this.object.equals(object.getClass().getCanonicalName())
        ? Ruling.ALLOW
        : Ruling.DENY
    );
  }

  
  /**
   * The action the rule applies to.
   */
  public enum Ruling {
    ALLOW,
    DENY
  }
}
