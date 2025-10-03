package de.paladinsinn.rollenspielcons.domain.api.locations;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.paladinsinn.rollenspielcons.domain.model.locations.PhysicalAddressImpl;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@JsonDeserialize(as = PhysicalAddressImpl.class)
public interface PhysicalAddress extends Location {
  /**
   * Full address of the location.
   *
   * @return full address
   */
  String getAddress();
  
  /**
   * The what3words address of the location.
   *
   * @return what3words address
   */
  String getThreeWords();
  
  /**
   * Longitude of the location.
   *
   * @return longitude
   */
  Double getLongitude();
  
  /**
   * Latitude of the location.
   *
   * @return latitude
   */
  Double getLatitude();
}
