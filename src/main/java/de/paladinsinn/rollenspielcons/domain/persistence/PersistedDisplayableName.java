package de.paladinsinn.rollenspielcons.domain.persistence;


import brave.internal.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
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
public class PersistedDisplayableName implements HasDisplayText {
  @Column(name = "name", length = 250, nullable = false)
  @NotNull
  private String name;
  
  @Column(name = "uri", length = 250)
  @Nullable
  private String uri;

  @JsonIgnore
  @Transient
  @Override
  public String getDisplayText() {
    return name;
  }
}
