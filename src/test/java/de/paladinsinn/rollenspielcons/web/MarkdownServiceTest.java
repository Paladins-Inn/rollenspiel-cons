package de.paladinsinn.rollenspielcons.web;


import de.paladinsinn.rollenspielcons.config.CacheConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Checks the Markdown conversion to sanitized HTML.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-25
 */
@SpringBootTest(classes = {
    MarkdownService.class,
    CacheConfig.class,
})
@Slf4j
public class MarkdownServiceTest {
  @Autowired
  private MarkdownService sut;
  
  @Test
  public void shouldReturnValidHtmlWhenCorrectMarkdownIsProvided() {
    log.trace("enter - ");
    
    String markdown = "# Hello World\nThis is a **bold** text with a [link](https://example.com).";
    
    String html = sut.toHtml(markdown);
    log.debug("Generated HTML:\n{}", html);
    
    assertTrue(html.contains("<h1>Hello World</h1>"), "H1 Title missing");
    assertTrue(html.contains("<strong>bold</strong>"), "Bold text missing");
    assertTrue(html.contains("<a href=\"https://example.com\" rel=\"nofollow\">link</a>"), "Link missing or not sanitized");
    
    log.trace("exit - ");
  }
  
  @Test
  public void shouldReturnSanitizedHtmlWhenMaliciousMarkdownIsProvided() {
    log.trace("enter - ");
    
    String markdown = "Click [here](javascript:alert('XSS')) to see something.";
    
    String html = sut.toHtml(markdown);
    log.debug("Generated HTML:\n{}", html);
    
    assertEquals("<p>Click here to see something.</p>\n", html, "Malicious link not sanitized");
    
    log.trace("exit - ");
  }
}
