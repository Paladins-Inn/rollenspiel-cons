package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Identity;
import de.paladinsinn.rollenspielcons.domain.api.iam.OidcUser;
import de.paladinsinn.rollenspielcons.domain.persistence.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.XSlf4j;

import static jakarta.persistence.FetchType.EAGER;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Entity(name = "OidcUser")
@Table(name = "OIDC_USERS")
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@XSlf4j
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class PersistentOidcUser extends AbstractBaseEntity implements OidcUser {
  @ManyToOne(fetch = EAGER, targetEntity = PersistentIdentity.class)
  @JoinColumn(name = "IDENTITY_ID", nullable = false)
  @NotNull
  private Identity identity;
  
  @Column(name = "ISSUER", length = 250, nullable = false)
  private String issuer;
  
  @Column(name = "SUB", length = 250, nullable = false)
  private String sub;
}
