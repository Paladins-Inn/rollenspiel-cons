package de.paladinsinn.rollenspielcons.services.geo;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Mapper(componentModel = "spring")
public interface GeoCoordinateMapper {
  @Mapping(target = "latitude", expression = "java(Double.parseDouble(result.getLatitude()))")
  @Mapping(target = "longitude", expression = "java(Double.parseDouble(result.getLongitude()))")
  GeoCoordinates toGeoCordinates(GeocodeMapsResult result);
}
