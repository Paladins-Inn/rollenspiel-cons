package de.paladinsinn.rollenspielcons.persistence.mapper;


import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.DisplayableName;
import de.paladinsinn.rollenspielcons.persistence.PersistedDisplayableName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Mapper(componentModel = "spring")
public interface DisplayTextMapper {
  DisplayableName toDisplayableName(PersistedDisplayableName entity);
  PersistedDisplayableName toPersistedDisplayableName(DisplayableName domain);
  
  @Mapping(target = "uri", source = "domain.uri")
  @Mapping(target = "name", source = "domain.displayText")
  PersistedDisplayableName toPersistedDisplayableName(HasDisplayText domain);
}
