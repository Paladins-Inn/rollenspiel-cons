package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.importers.RefreshTokenAuthentication;
import de.paladinsinn.rollenspielcons.domain.model.importers.UsernameAndPasswordAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Mapper(
    componentModel = "spring",
    uses = {
        UsernameAndPasswordAuthenticationMapper.class,
        RefreshTokenAuthenticationMapper.class
    }
)
public interface ImportAuthenticationMapper {
  UsernameAndPasswordAuthentication toDomain(JpaUsernameAndPasswordAuthentication jpa);
  JpaUsernameAndPasswordAuthentication toJpa(UsernameAndPasswordAuthentication domain);
  
  RefreshTokenAuthentication toDomain(JpaRefreshTokenAuthentication jpa);
  @Mapping(target = "password", source = "refreshToken")
  JpaRefreshTokenAuthentication toJpa(RefreshTokenAuthentication domain);
  
  default ImporterAuthentication toDomain(final ImporterAuthentication jpa) {
    if (jpa instanceof JpaUsernameAndPasswordAuthentication) {
      return toDomain((JpaUsernameAndPasswordAuthentication) jpa);
    } else if (jpa instanceof JpaRefreshTokenAuthentication) {
      return toDomain((JpaRefreshTokenAuthentication) jpa);
    }
    
    throw new IllegalArgumentException("Unknown authentication type: " + jpa.getClass().getName());
  }
  
  default AbstractJpaAuthenticationBase toJpa(final ImporterAuthentication domain) {
    if (domain instanceof UsernameAndPasswordAuthentication) {
      return toJpa((UsernameAndPasswordAuthentication) domain);
    } else if (domain instanceof de.paladinsinn.rollenspielcons.domain.model.importers.RefreshTokenAuthentication) {
      return toJpa((RefreshTokenAuthentication) domain);
    }
    
    throw new IllegalArgumentException("Unknown authentication type: " + domain.getClass().getName());
  }
}
