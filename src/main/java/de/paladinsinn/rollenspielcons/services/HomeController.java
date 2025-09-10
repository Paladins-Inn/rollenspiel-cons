package de.paladinsinn.rollenspielcons.services;

import de.paladinsinn.rollenspielcons.ui.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
      Page home = Page.builder()
          .title("Willkommen bei Rollenspiel-Cons!")
          .description("Die Seite für Rollenspiel-Conventions")
          .build();
      
      model.addAttribute("page", home);
      return "home.jte";
    }
}