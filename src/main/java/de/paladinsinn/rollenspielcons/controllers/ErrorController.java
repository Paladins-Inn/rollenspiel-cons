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

import de.paladinsinn.rollenspielcons.web.Page;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
  
  @PermitAll
  @RequestMapping("/error")
  public String handleError(@AuthenticationPrincipal OAuth2User user, HttpServletRequest request, Model model) {
    log.trace("enter - {}", new Object[] {user, request});
    
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
  
    Page page = Page.builder()
                    .user(user)
                    .build();
  
    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());
      
      model.addAttribute("statusCode", statusCode);
      model.addAttribute("errorMessage", errorMessage != null ? errorMessage.toString() : "Unbekannter Fehler");
      model.addAttribute("requestUri", requestUri != null ? requestUri.toString() : "");
      model.addAttribute("page", page);
  
      // Spezifische Templates für verschiedene HTTP-Status-Codes
      if (statusCode == HttpStatus.NOT_FOUND.value()) {
          return "pages/error/404";
      } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
          return "pages/error/403";
      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
          return "pages/error/500";
      }
    }
    
    return "pages/error/general";
  }
  
  @PermitAll
  @RequestMapping("/login")
  public String loginPage(
      @RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout,
      Model model
  ) {
    log.trace("enter - {}", new Object[] {error, logout});
    
    if (error != null) {
      model.addAttribute("loginError", true);
      model.addAttribute("errorMessage", "Anmeldung fehlgeschlagen. Bitte versuchen Sie es erneut.");
  }
    
    if (logout != null) {
      model.addAttribute("logoutSuccess", true);
      model.addAttribute("logoutMessage", "Sie wurden erfolgreich abgemeldet.");
  }
    
    return "pages/login";
  }
}
