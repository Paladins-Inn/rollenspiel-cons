package de.paladinsinn.rollenspielcons.services.caldav;

import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.domain.api.time.TimeSpec;
import de.paladinsinn.rollenspielcons.domain.model.DisplayableName;
import de.paladinsinn.rollenspielcons.domain.model.events.EventImpl;
import de.paladinsinn.rollenspielcons.domain.model.locations.PhysicalAddressImpl;
import de.paladinsinn.rollenspielcons.domain.model.locations.WebLocationImpl;
import de.paladinsinn.rollenspielcons.domain.model.time.TimeSpecImpl;
import de.paladinsinn.rollenspielcons.persistence.mapper.LocationMapper;
import de.paladinsinn.rollenspielcons.services.geo.GeoCoordinates;
import de.paladinsinn.rollenspielcons.services.geo.LocationService;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;

@Mapper(
    componentModel = "spring",
    uses = {
        LocationMapper.class
    }
)
public interface VEventMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "importDefinition", ignore = true)
  @Mapping(target = "externalId", expression = "java(uidToExternalId(vEvent.getUid()))")
  @Mapping(target = "name", source = "summary")
  @Mapping(target = "temporalData", expression = "java(getTemporalData(vEvent))")
  @Mapping(target = "description", expression = "java(descriptionToDescription(vEvent.getDescription()))")
  @Mapping(target = "labels", expression = "java(labelsFrom(vEvent))")
  @Mapping(target = "locations", expression = "java(calculatePhysicalLocations(vEvent, locationService))")
  @Mapping(target = "webLocations", expression = "java(calculateWebLocations(vEvent))")
  @Mapping(target = "website", expression = "java(calculateWebLocation(vEvent))")
  EventImpl toEvent(VEvent vEvent, @Context final LocationService locationService);
  
  default String uidToExternalId(final Optional<Uid> uid) {
    return uid.map(Uid::getValue).orElse(null);
  }
  
  default String descriptionToDescription(final Optional<Description> description) {
    return description.map(Description::getValue).orElse(null);
  }
  
  default DisplayableName summaryToName(final Optional<Summary> summary) {
    return summary.isPresent()
           ? DisplayableName.builder().name(summary.get().getValue()).build()
           : DisplayableName.builder().name("-no name given-").build();
  }
  
  
  default Set<PhysicalAddress> calculatePhysicalLocations(final VEvent vEvent, @Context final LocationService locationService) {
    if (vEvent.getLocation().isPresent()) {
      String         address     = vEvent.getLocation().get().getValue();
      GeoCoordinates coordinates = locationService.convertAddressToCoordinates(address);
      
      if (coordinates == null) {
        return Set.of();
      }
      
      return Set.of(PhysicalAddressImpl.builder()
          .address(address)
           .threeWords(locationService.convertTo3Words(
               coordinates.getLatitude(),
               coordinates.getLongitude(),
               Locale.getDefault().getCountry()
           ))
           .latitude(coordinates.getLatitude())
           .longitude(coordinates.getLongitude())
          .build());
    }
    
    return Set.of();
  }

  default WebLocation calculateWebLocation(final VEvent vEvent) {
    return WebLocationImpl
        .builder()
        .uri(vEvent.getLocation().orElse(new net.fortuna.ical4j.model.property.Location()).getValue())
        .build();
  }
  
  default Set<WebLocation> calculateWebLocations(final VEvent vEvent) {
    return Set.of(calculateWebLocation(vEvent));
  }
  
  default Set<String> labelsFrom(VEvent vEvent) {
    // zuerst standard CATEGORIES prüfen
    Optional<Property> categories = vEvent.getProperty(Property.CATEGORIES);
    if (categories.isPresent()) {
      String val = categories.get().getValue();
      return Arrays.stream(val.split(","))
                   .map(String::trim)
                   .filter(s -> !s.isEmpty())
                   .collect(Collectors.toSet());
    }
    
    // alternativ: eigene X-LABELS-Property
    Optional<Property> xLabels = vEvent.getProperty("X-LABELS");
    if (xLabels.isPresent()) {
      String val = xLabels.get().getValue();
      return Arrays.stream(val.split(","))
                   .map(String::trim)
                   .filter(s -> !s.isEmpty())
                   .collect(Collectors.toSet());
    }
    
    return Collections.emptySet();
  }
  
  default TimeSpec getTemporalData(final VEvent vEvent) {
    return vEvent.getDateTimeStart()
            .map(t -> TimeSpecImpl.builder()
               .startDay(
                   dtStartToZonedDateTime(t,ZoneId.systemDefault())
               )
               .duration(
                   toZonedRange(t, vEvent.getDateTimeEnd(),ZoneId.systemDefault())
               )
               .build()
            )
            .orElse(null);
    
  }
  
  default ZonedDateTime dtStartToZonedDateTime(DtStart<Temporal> dtStart, ZoneId fallbackZone) {
    return temporalToZonedDateTime(
        dtStart.getDate(),
        getTimeZone(dtStart, fallbackZone)
    );
  }
  
  default ZoneId getTimeZone(DtStart<Temporal> dtStart, ZoneId fallbackZone) {
    if (dtStart.isUtc()) {
      return ZoneOffset.UTC;
    }
    
    Optional<Parameter> tzid = dtStart.getParameter("TZID");
    return tzid.map(Parameter::getValue).map(ZoneId::of).orElse(fallbackZone);
  }
  
  default ZonedDateTime dtEndToZonedDateTime(DtStart<Temporal> dtStart, @Nullable DtEnd<Temporal> dtEnd, ZoneId fallbackZone) {
    if (dtEnd == null || dtEnd.getDate() == null) {
      // return end of the start day
      return dtStartToZonedDateTime(dtStart, fallbackZone)
          .plusDays(1L)
          .minusNanos(1L);
    }
    
    return temporalToZonedDateTime(
        dtEnd.getDate(),
        getTimeZone(dtEnd, fallbackZone)
    );
  }
  
  default ZoneId getTimeZone(@NotNull DtEnd<Temporal> dtEnd, @NotNull ZoneId fallbackZone) {
    return dtEnd.isUtc()
           ? ZoneOffset.UTC
           : dtEnd.getParameter("TZID").map(Parameter::getValue).map(ZoneId::of).orElse(fallbackZone);
  }
  
  default ZonedDateTime temporalToZonedDateTime(@NotNull Temporal d, @NotNull ZoneId zone) {
    return temporalToInstant(d, zone).atZone(zone);
  }
  
  default Instant temporalToInstant(final Temporal data, ZoneId zone) {
    long e;
    long nanos;
    
    if (data instanceof LocalDate) {
      return Instant.from(((LocalDate) data).atStartOfDay().atZone(zone));
    }
    
    if (data.isSupported(INSTANT_SECONDS)) {
      e = data.getLong(INSTANT_SECONDS);
      nanos = data.isSupported(NANO_OF_SECOND) ? data.get(NANO_OF_SECOND) : 0;
    } else {
      throw new IllegalArgumentException("Temporal type not supported: " + data.getClass().getName());
    }
    
    return Instant.ofEpochSecond(e, nanos);
  }
  
  /**
   * Liefert Start und Ende als Duration. Falls dtEndOpt leer ist,
   * wird das Ende des Tages von dtStart verwendet.
   */
  default Duration toZonedRange(final DtStart<Temporal> dtStart,
                              final Optional<DtEnd<Temporal>> dtEndOpt,
                              final ZoneId fallbackZone) {
    ZonedDateTime startZ = dtStartToZonedDateTime(dtStart, fallbackZone);
    if (startZ == null) {
      return Duration.ZERO;
    }
    
    ZonedDateTime endZ = dtEndToZonedDateTime(dtStart, dtEndOpt.orElse(null), fallbackZone);
    
    return Duration.between(startZ, endZ);
  }
}