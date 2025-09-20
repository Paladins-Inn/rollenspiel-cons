package de.paladinsinn.rollenspielcons.ui;

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
  
  
  @GetMapping("/about")
  public String about(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/about");
  }
  
  
  @GetMapping("/tos")
  public String tos(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/tos");
  }
  
  
  @GetMapping("/dataprotection")
  public String dataprotection(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/dataprotection");
  }
  
  
  @GetMapping("/imprint")
  public String imprint(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/legalese/imprint");
  }
  
  
}