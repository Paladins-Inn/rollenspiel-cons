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

package de.paladinsinn.rollenspielcons.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalModelAttributes {
  private final MarkdownService markdownService;

  @Value("${server.servlet.context-path:}")
  private String contextPath;
  
  @ModelAttribute("md")
  public MarkdownService markdownService() {
    log.trace("enter - ");
    
    log.trace("exit - {}", markdownService);
    return markdownService;
  }
  
  @ModelAttribute("contextPath")
  public String contextPath() {
    log.trace("enter - ");

    log.trace("exit - {}", contextPath);
    return contextPath;
  }
  
  @ModelAttribute("page")
  public Page page(@AuthenticationPrincipal OAuth2User user) {
    log.trace("enter -  {}", user);
    
    Page result = Page
        .builder()
        .user(user)
        .build();
    
    log.trace("exit - {}", result);
    return result;
  }
}

