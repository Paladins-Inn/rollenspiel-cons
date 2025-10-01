package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractBaseEntity;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@SuperBuilder(toBuilder = true)
@Getter
@Setter(value = AccessLevel.PACKAGE, onMethod_ = @__(@Deprecated)) // Only for testing
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractBaseEntity implements HasId, HasDisplayText, HasOwner {
  @EqualsAndHashCode.Exclude
  @NotNull(message = "The owner must not be null.")
  private Owner owner;
  
  @Builder.Default
  private Set<Rule> rules = new HashSet<>();
}
