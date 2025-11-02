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