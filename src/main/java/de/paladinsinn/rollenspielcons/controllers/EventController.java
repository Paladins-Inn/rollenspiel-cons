package de.paladinsinn.rollenspielcons.controllers;


import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.persistence.events.EventRepository;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static de.paladinsinn.rollenspielcons.domain.api.MimeTypes.APPLICATION_JSON_1;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
@Controller
@RequestMapping("/events2")
@RequiredArgsConstructor
@XSlf4j
public class EventController extends AbstractBaseController {
  private final EventRepository eventRepository;
  
  // FIXME 2025-10-03 klenkes74: implement the API fully
  @PermitAll
  @GetMapping(
      value = "/",
      produces = { APPLICATION_JSON_1, APPLICATION_JSON_VALUE },
      consumes = APPLICATION_JSON_VALUE
  )
  public List<Event> index() {
    log.entry();
    
    return log.exit(List.of());
  }
}
