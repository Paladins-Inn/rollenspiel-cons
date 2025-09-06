package de.paladinsinn.rollenspielcons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Willkommen bei Rollenspiel-Cons!");
        return "home.jte";
    }
}