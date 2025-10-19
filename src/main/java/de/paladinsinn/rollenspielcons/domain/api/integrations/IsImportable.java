package de.paladinsinn.rollenspielcons.domain.api.integrations;


import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import javax.annotation.Nullable;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * This interface marks importable entities.
 *
 * <p>They are specified by the name of the importer (an unique string) and the external id of the entity.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
public interface IsImportable {
  /**
   * @return the import definition of the entity.
   */
  default ImportDefinition getImportDefinition() {
    return ImportDefinition.EMPTY;
  }
  
  /**
   * @return true if the entity is imported.
   */
  default boolean isImported() {
    return getExternalId().isPresent();
  }
  
  /**
   * @return the external id of the entity.
   */
  @Schema(
      title = "The external ID",
      description = "The ID of this entity in the external source.",
      nullable = true,
      examples = {
          "0s5samkb7jcd7kdl2i2j1j00ag",
          "7dg9jus7nb2pi7e0kk02180oik"
      }
  )
  @Nullable
  @Size(min = 1, max = 50, message = "The external ID must be between {min} and {max} characters long.")
  Optional<String> getExternalId();
  
  /**
   * @return the timestamp of the last synchronization.
   */
  default OffsetDateTime getLastSyncTime() {
    return OffsetDateTime.now(ZoneId.systemDefault());
  }
}
