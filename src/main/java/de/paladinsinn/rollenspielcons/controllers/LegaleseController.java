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

import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legalese")
@Slf4j
public class LegaleseController {
  
  @PermitAll
  @GetMapping("/about")
  public String about(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.trace("enter - {}", new Object[] {user, model});
    
    log.trace("exit - pages/legalese/about");
    return "pages/legalese/about";
  }
  
  
  @PermitAll
  @GetMapping("/tos")
  public String tos(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.trace("enter - {}", new Object[] {user, model});

    log.trace("exit - pages/legalese/tos");
    return "pages/legalese/tos";
  }
  
  
  @PermitAll
  @GetMapping("/dataprotection")
  public String dataprotection(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.trace("enter - {}", new Object[] {user, model});
    
    log.trace("exit - pages/legalese/dataprotection");
    return "pages/legalese/dataprotection";
  }
  
  
  @PermitAll
  @GetMapping("/imprint")
  public String imprint(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.trace("enter - {}", new Object[] {user, model});

    log.trace("exit - pages/legalese/imprint");
    return "pages/legalese/imprint";
  }
  
  
}