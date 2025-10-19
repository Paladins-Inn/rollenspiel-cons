package de.paladinsinn.rollenspielcons.persistence.mapper;


import de.paladinsinn.rollenspielcons.persistence.EmbeddableOwner;
import org.mapstruct.Mapper;


/**
 * Maps the owner to the database embeddable object.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Mapper(componentModel = "spring")
public interface OwnerMapper {
  default EmbeddableOwner toEmbeddableOwner(final String owner) {
    return EmbeddableOwner.builder().owner(owner).build();
  }
  
  default String toOwner(final EmbeddableOwner owner) {
    return owner.getOwner();
  }
}
