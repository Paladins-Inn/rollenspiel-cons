/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
