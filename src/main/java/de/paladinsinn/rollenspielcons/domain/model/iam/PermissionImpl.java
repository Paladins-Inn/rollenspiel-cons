package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import de.paladinsinn.rollenspielcons.domain.api.iam.Action;
import de.paladinsinn.rollenspielcons.domain.api.iam.Permission;
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
public class PermissionImpl extends AbstractModelBase implements Permission {
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
}
