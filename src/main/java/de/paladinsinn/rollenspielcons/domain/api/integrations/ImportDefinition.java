package de.paladinsinn.rollenspielcons.domain.api.integrations;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import static de.paladinsinn.rollenspielcons.domain.api.integrations.ImportDefinition.ImportType.NONE;

/**
 * The definition of an import.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-11
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ImportDefinition {
  /**
   * The empty definition.
   *
   * <p>This means, the entity is not imported at all.</p>
   */
  public static final ImportDefinition EMPTY = ImportDefinition
      .builder()
      .name("none")
      .type(NONE)
      .build();

  @NotBlank(message = "Name must not be blank")
  private String name;
  
  @NotNull(message = "Type must be set")
  private ImportType type;
  
  public enum ImportType {
    NONE,
    CALENDAR,
    SPEAKER,
    PARTICIPANT;
  }
}
