package de.paladinsinn.rollenspielcons.persistence;


import jakarta.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;


/**
 * The embeddable representation of a displayable name with an optional URI.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Embeddable
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"name", "uri"})
public class PersistedDisplayableName implements HasDisplayText, Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Column(name = "NAME", length = 250, nullable = false)
  @NotBlank(message = "The name must not be blank.")
  private String name;
  
  @Column(name = "URI", length = 250)
  @Nullable
  private String uri;

  @JsonIgnore
  @Transient
  @Override
  public String getDisplayText() {
    return name;
  }
}
