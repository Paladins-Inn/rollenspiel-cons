package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import de.paladinsinn.rollenspielcons.domain.api.iam.Role;
import de.paladinsinn.rollenspielcons.domain.api.iam.Permission;
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
public class RoleImpl extends AbstractModelBase implements Role {
  @EqualsAndHashCode.Exclude
  @NotNull(message = "The owner must not be null.")
  private Owner owner;
  
  @Builder.Default
  private Set<Permission> permissions = new HashSet<>();
}
