package de.paladinsinn.rollenspielcons.web;


import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;


/**
 *
 *
 * @author klenkes74
 * @since 20.09.25
 */
@XSlf4j
public class AbstractBaseController {
  @Value("${server.servlet.context-path:}")
  private String contextPath;
  
  protected String forwarder(OAuth2User user, Model model, String path) {
    log.entry(contextPath, path, user, model);
    Page aboutPage = Page.builder()
        .user(user)
        .build();
    
    model.addAttribute("contextPath", contextPath);
    model.addAttribute("page", aboutPage);
    
    return log.exit(path);
  }
}
