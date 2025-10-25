package de.paladinsinn.rollenspielcons.controllers;

import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legalese")
@XSlf4j
public class LegaleseController extends AbstractBaseController {
  
  @PermitAll
  @GetMapping("/about")
  public String about(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/about");
  }
  
  
  @PermitAll
  @GetMapping("/tos")
  public String tos(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/tos");
  }
  
  
  @PermitAll
  @GetMapping("/dataprotection")
  public String dataprotection(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/dataprotection");
  }
  
  
  @PermitAll
  @GetMapping("/imprint")
  public String imprint(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/imprint");
  }
  
  
}