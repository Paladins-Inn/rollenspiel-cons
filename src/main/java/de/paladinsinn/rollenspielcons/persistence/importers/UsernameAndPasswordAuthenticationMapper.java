package de.paladinsinn.rollenspielcons.persistence.importers;


import de.paladinsinn.rollenspielcons.domain.model.importers.UsernameAndPasswordAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Mapper(componentModel = "spring")
public interface UsernameAndPasswordAuthenticationMapper {
  @Mapping(target = "password", source = "jpa.password")
  UsernameAndPasswordAuthentication toDomain(JpaUsernameAndPasswordAuthentication jpa);
  
  @Mapping(target = "password", source = "domain.password")
  JpaUsernameAndPasswordAuthentication toJpa(UsernameAndPasswordAuthentication domain);
}
