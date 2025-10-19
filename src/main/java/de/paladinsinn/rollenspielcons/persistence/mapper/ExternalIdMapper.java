package de.paladinsinn.rollenspielcons.persistence.mapper;


import java.util.Optional;
import org.mapstruct.Mapper;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Mapper(componentModel = "spring")
public interface ExternalIdMapper {
  default String toExternalId(Optional<String> externalId) {
    return externalId.orElse(null);
  }
  
  default Optional<String> toExternalId(String externalId) {
    return Optional.ofNullable(externalId);
  }
}
