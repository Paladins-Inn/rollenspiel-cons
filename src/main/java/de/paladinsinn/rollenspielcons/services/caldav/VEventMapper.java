package de.paladinsinn.rollenspielcons.services.caldav;

import de.paladinsinn.rollenspielcons.domain.api.locations.Location;
import de.paladinsinn.rollenspielcons.domain.model.events.PhysicalEventImpl;
import de.paladinsinn.rollenspielcons.domain.model.events.WebEventImpl;
import de.paladinsinn.rollenspielcons.domain.model.locations.PhysicalAddressImpl;
import de.paladinsinn.rollenspielcons.domain.model.locations.WebLocationImpl;
import de.paladinsinn.rollenspielcons.services.geo.GeoCordinates;
import de.paladinsinn.rollenspielcons.services.geo.LocationService;
import net.fortuna.ical4j.model.component.VEvent;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VEventMapper {
  @Mapping(target = "locations", expression = "java(calculatePhysicalLocation(vEvent, locationService))")
  PhysicalEventImpl toPhysicalEvent(VEvent vEvent, @Context final LocationService locationService);
  
  default Location calculatePhysicalLocation(VEvent vEvent, @Context final LocationService locationService) {
    if (vEvent.getLocation().isPresent()) {
      String address = vEvent.getLocation().get().getValue();
      GeoCordinates coordinates = locationService.convertAddressToCoordinates(address);
      
      return PhysicalAddressImpl.builder()
          .address(address)
           .threeWords(locationService.convertTo3Words(coordinates.getLatitude(), coordinates.getLongitude()))
           .latitude(coordinates.getLatitude())
           .longitude(coordinates.getLongitude())
          .build();
    }
    
    return null;
  }

  @Mapping(target = "locations", expression = "java(calculateWebLocation(vEvent)")
  WebEventImpl toWebEvent(VEvent vEvent);
  
  default Location calculateWebLocation(VEvent vEvent) {
    return WebLocationImpl
        .builder()
        .uri(vEvent.getLocation().orElse(new net.fortuna.ical4j.model.property.Location()).getValue())
        .build();
  }
}