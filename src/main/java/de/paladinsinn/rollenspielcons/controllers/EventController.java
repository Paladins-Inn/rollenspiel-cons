/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.paladinsinn.rollenspielcons.controllers;


import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.persistence.events.JpaEventRepository;
import jakarta.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EventController {
  private final JpaEventRepository eventRepository;
  
  // FIXME 2025-10-03 klenkes74: implement the API fully
  @PermitAll
  @GetMapping(
      value = "/",
      produces = { APPLICATION_JSON_1, APPLICATION_JSON_VALUE },
      consumes = APPLICATION_JSON_VALUE
  )
  public List<Event> index() {
    log.trace("enter - ");
    
    var result = new ArrayList<Event>();

    log.trace("exit - {}", result);
    return result;
  }
}
