package de.paladinsinn.rollenspielcons.web;

import de.paladinsinn.rollenspielcons.services.markdown.MarkdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
@XSlf4j
public class GlobalModelAttributes {
  private final MarkdownService markdownService;

  
  @ModelAttribute("md")
  public MarkdownService markdownService() {
    return markdownService;
  }
}

