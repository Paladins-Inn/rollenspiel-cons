package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Action;
import de.paladinsinn.rollenspielcons.domain.api.iam.Permission;
import de.paladinsinn.rollenspielcons.domain.persistence.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import static jakarta.persistence.EnumType.STRING;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Entity(name = "Permission")
@Table(name = "PERMISSIONS")
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PersistentPermission extends AbstractBaseEntity implements Permission {
  /**
   * The action that is protected. Defaults to {@link Action#READ} when not set.
   */
  @Column(name = "ACTION", length = 10, nullable = false)
  @Enumerated(STRING)
  @NotNull(message = "The action must not be null.")
  @Builder.Default
  private Action action = Action.READ;
  
  /**
   * The object the rule applies to.
   * The object is normally the {@link Class#getCanonicalName()} of a resource class.
   */
  @Column(name = "OBJECT", length = 255, nullable = false)
  @NotNull(message = "The object must not be null.")
  @Size(min = 1, max = 255, message = "The object must be between {min} and {max} characters long.")
  private String object;
  
  /**
   * The ruling of this access rule. Defaults to {@link Permission.Ruling#ALLOW} when not set.
   */
  @Column(name = "RULING", length = 10, nullable = false)
  @Enumerated(STRING)
  @NotNull(message = "The ruling must be set.")
  @Builder.Default
  private Permission.Ruling ruling = Permission.Ruling.ALLOW;
}
