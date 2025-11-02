package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.model.importers.RefreshTokenAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Mapper(componentModel = "spring")
public interface RefreshTokenAuthenticationMapper {
  RefreshTokenAuthentication toDomain(JpaRefreshTokenAuthentication jpa);
  
  @Mapping(target = "password", source = "refreshToken")
  JpaRefreshTokenAuthentication toJpa(RefreshTokenAuthentication domain);
}
