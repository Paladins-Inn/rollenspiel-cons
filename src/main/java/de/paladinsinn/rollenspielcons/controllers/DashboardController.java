package de.paladinsinn.rollenspielcons.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
@Slf4j
public class DashboardController {
  
  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public String dashboard(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.trace("enter -  {}, {}", user, model);
    
    return "pages/dashboard";
  }
}
