package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import de.paladinsinn.rollenspielcons.domain.api.iam.Permission;
import de.paladinsinn.rollenspielcons.domain.api.iam.Role;
import de.paladinsinn.rollenspielcons.domain.persistence.AbstractBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Entity(name = "Role")
@Table(name = "ROLES")
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PersistentRole extends AbstractBaseEntity implements Role {
  @ManyToOne(fetch = EAGER, optional = true, targetEntity = PersistentOwner.class)
  @JoinColumn(name = "OWNER_ID", nullable = true)
  private Owner owner;
  
  @ManyToMany(fetch = EAGER, cascade = ALL, targetEntity = PersistentPermission.class)
  @JoinTable(
      name = "ROLE_PERMISSIONS",
      joinColumns = @JoinColumn(name = "ROLE_ID"),
      inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID")
  )
  @Builder.Default
  private Set<Permission> permissions = new HashSet<>();
}
