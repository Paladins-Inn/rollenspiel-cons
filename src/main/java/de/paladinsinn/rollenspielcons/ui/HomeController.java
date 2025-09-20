package de.paladinsinn.rollenspielcons.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
  
    @GetMapping("/home")
    public String home(Principal principal, Model model) {
      Page home = Page.builder()
          .title("Willkommen bei Rollenspiel-Cons!")
          .author("Paladins Inn")
          .description("Die Seite für Rollenspiel-Conventions")
          .principal(principal)
          .build();
      
      model.addAttribute("page", home);
      return "home";
    }
    
    @GetMapping("/")
    public String index(Principal principal, Model model) {
      return home(principal, model);
    }
}