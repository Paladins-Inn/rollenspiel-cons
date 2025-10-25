package de.paladinsinn.rollenspielcons.controllers;


import de.paladinsinn.rollenspielcons.web.Page;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;


/**
 * Base Controller for handling common stuff in controllers
 *
 * @author klenkes74
 * @since 20.09.25
 */
@XSlf4j
public abstract class AbstractBaseController {
  protected String forwarder(OAuth2User user, Model model, String path) {
    log.entry(path, user, model);
    Page aboutPage = Page.builder()
                         .user(user)
                         .build();
    
    model.addAttribute("page", aboutPage);
    
    return log.exit(path);
  }
}
