package de.paladinsinn.rollenspielcons.services.caldav;

import de.paladinsinn.rollenspielcons.domain.model.events.PhysicalEventImpl;
import de.paladinsinn.rollenspielcons.domain.model.events.WebEventImpl;
import net.fortuna.ical4j.model.component.VEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VEventMapper {
  @Mapping(target = "locations", expression = "java(/* Mapping von VEvent zu Set<PhysicalAddress> */)")
  PhysicalEventImpl toPhysicalEvent(VEvent vEvent);

  @Mapping(target = "locations", expression = "java(/* Mapping von VEvent zu Set<WebLocation> */)")
  WebEventImpl toWebEvent(VEvent vEvent);
}