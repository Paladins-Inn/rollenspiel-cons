package de.paladinsinn.rollenspielcons.ui;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
@XSlf4j
public class LogoutController {
  
  @PermitAll
  @GetMapping
  public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    log.entry(request, response, authentication);
    
    if (authentication != null) {
      log.info("Logging out {}", authentication.getName());
      new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
        
    return log.exit("redirect:/");
  }
}
