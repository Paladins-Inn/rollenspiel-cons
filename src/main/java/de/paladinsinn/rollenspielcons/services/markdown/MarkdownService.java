package de.paladinsinn.rollenspielcons.services.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {
  private static final Logger log = LoggerFactory.getLogger(MarkdownService.class);

  private final Parser parser;
  private final HtmlRenderer renderer;
  private final PolicyFactory sanitizer;

  public MarkdownService() {
    this.parser = Parser.builder().build();
    this.renderer = HtmlRenderer.builder().build();
    this.sanitizer = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
  }

  /**
   * Convert Markdown to sanitized HTML. Cached via Spring Cache.
   *
   * @param markdown markdown source
   * @return sanitized HTML (never null)
   */
  @Cacheable(value = "markdown", keyGenerator = "contentHashKeyGenerator")
  public String toHtml(String markdown) {
    if (markdown == null || markdown.isEmpty()) return "";
    try {
      Node doc = parser.parse(markdown);
      String html = renderer.render(doc);
      return sanitizer.sanitize(html);
    } catch (Exception e) {
      log.warn("Failed to render markdown", e);
      return "";
    }
  }
}

