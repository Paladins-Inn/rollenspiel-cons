package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for {@link PersistentOidcUser}.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-02
 */
public interface OidcUserRepository extends JpaRepository<PersistentOidcUser, Long> {
  PersistentOidcUser findByIssuerAndSub(String issuer, String sub);
}
