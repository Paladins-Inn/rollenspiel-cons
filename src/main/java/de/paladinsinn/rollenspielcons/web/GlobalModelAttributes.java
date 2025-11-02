package de.paladinsinn.rollenspielcons.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalModelAttributes {
  private final MarkdownService markdownService;

  @Value("${server.servlet.context-path:}")
  private String contextPath;
  
  @ModelAttribute("md")
  public MarkdownService markdownService() {
    log.trace("enter - ");
    
    var result = markdownService;

    log.trace("exit - {}", result);
    return result;
  }
  
  @ModelAttribute("contextPath")
  public String contextPath() {
    log.trace("enter - ");

    log.trace("exit - {}", contextPath);
    return contextPath;
  }
  
  @ModelAttribute("page")
  public Page page(@AuthenticationPrincipal OAuth2User user) {
    log.trace("enter -  {}", user);
    
    Page result = Page
        .builder()
        .user(user)
        .build();
    
    log.trace("exit - {}", result);
    return result;
  }
}

