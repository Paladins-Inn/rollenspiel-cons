package de.paladinsinn.rollenspielcons.domain.persistence.iam;


import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for {@link PersistentIdentity}.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-02
 */
public interface IdentityRepository extends JpaRepository<PersistentIdentity, Long> {
}
