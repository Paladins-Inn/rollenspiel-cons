package de.paladinsinn.rollenspielcons.services.geo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
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
 * @since 2025-10-18
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(of = {"placeId"})
public class GeocodeMapsResult {
  @ToString.Include
  @JsonProperty("place_id")
  private String placeId;
  
  private String licence;
  
  @JsonProperty("osm_type")
  private String osmType;
  
  @ToString.Include
  @JsonProperty("osm_id")
  private String osmId;
  
  private String[] boundingbox;
  
  @ToString.Include
  @JsonProperty("lat")
  private String latitude;
  @ToString.Include
  @JsonProperty("lon")
  private String longitude;
  @JsonProperty("display_name")
  private String displayName;
  @JsonProperty("class")
  private String locationClass;
  private String type;
  
  @Nullable
  private Double importance;

  @JsonIgnore
  public GeoCoordinates getGeoCoordinates() {
    return GeoCoordinates
        .builder()
        .latitude(Double.parseDouble(latitude))
        .longitude(Double.parseDouble(longitude))
        .build();
  }
}
