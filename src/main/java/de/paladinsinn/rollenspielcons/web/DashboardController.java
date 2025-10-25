package de.paladinsinn.rollenspielcons.web;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/dashboard")
@XSlf4j
public class DashboardController extends AbstractBaseController {
  
  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public String dashboard(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    Page dashboardPage = Page.builder()
        .user(user)
        .build();
    
    return forwarder(user, model, "pages/dashboard");
  }
}
