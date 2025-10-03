package de.paladinsinn.rollenspielcons.domain.persistence;


import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@Embeddable
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmbeddableOwner implements HasOwner, Serializable {
  private static final long serialVersionUID = 1L;
  
  @Column(name = "OWNER", length = 100, nullable = false)
  @EqualsAndHashCode.Include
  @NotBlank(message = "The embeddableOwner must be set.")
  @Builder.Default
  private String owner = "system";
  
  @Override
  public String toString() {
    return owner;
  }
}
