package de.paladinsinn.rollenspielcons.persistence.time;


import de.paladinsinn.rollenspielcons.domain.api.time.TimeSpec;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
public class EmbeddableTimeSpec implements TimeSpec, Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Column(name = "START", nullable = false)
  @NotNull(message = "Start day must be set")
  @ToString.Include
  @EqualsAndHashCode.Include
  private ZonedDateTime startDay;
  
  @Column(name = "DURATION")
  @Nullable
  @ToString.Include
  @EqualsAndHashCode.Include
  private Duration duration;
}
