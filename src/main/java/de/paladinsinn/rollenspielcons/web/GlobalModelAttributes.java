package de.paladinsinn.rollenspielcons.web;

import de.paladinsinn.rollenspielcons.services.markdown.MarkdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
@XSlf4j
public class GlobalModelAttributes {
  private final MarkdownService markdownService;

  @Value("${server.servlet.context-path:}")
  private String contextPath;
  
  @ModelAttribute("md")
  public MarkdownService markdownService() {
    log.entry();
    return log.exit(markdownService);
  }
  
  @ModelAttribute("contextPath")
  public String contextPath() {
    log.entry();
    return log.exit(contextPath);
  }
}

