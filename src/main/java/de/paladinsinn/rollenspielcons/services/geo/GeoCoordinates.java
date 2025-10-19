package de.paladinsinn.rollenspielcons.services.geo;


import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-06
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class GeoCoordinates implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  
  /** The latitude of the location. */
  private double latitude;
  
  /** The longitude of the location. */
  private double longitude;
}
