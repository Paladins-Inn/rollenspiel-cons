package de.paladinsinn.rollenspielcons.web;


import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * Handles conversion from Markdown to sanitized HTML.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-25
 */
@Service
@Slf4j
public class MarkdownService {
  private final Parser parser;
  private final HtmlRenderer renderer;
  private final PolicyFactory sanitizer;
  
  public MarkdownService() {
    this.parser = Parser.builder().build();
    this.renderer = HtmlRenderer.builder().build();
    this.sanitizer = Sanitizers
        .FORMATTING
        .and(Sanitizers.LINKS)
        .and(Sanitizers.BLOCKS)
        .and(Sanitizers.STYLES)
        .and(Sanitizers.IMAGES)
        .and(Sanitizers.TABLES);
  }
  
  @Cacheable(value="markDown", keyGenerator = "contentHashKeyGenerator")
  public String toHtml(@NotBlank String markdown) {
    log.trace("enter -  {}", markdown.length());
    
    String result = sanitizer.sanitize(renderer.render(parser.parse(markdown)));
    
    log.trace("Exit marker with: {}", result.length());
    return result;
  }
}
