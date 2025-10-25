package de.paladinsinn.rollenspielcons.web;


import gg.jte.Content;
import gg.jte.TemplateOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */

@RequiredArgsConstructor
@XSlf4j
public class MarkdownContent implements Content {
    private final MarkdownService markdownService;
    private final String markdown;

    @Override
    public void writeTo(TemplateOutput output) {
      log.entry(output);
      
      output.writeContent(markdownService.toHtml(markdown));
      
      log.exit();
    }

    public static MarkdownContent of(MarkdownService markdownService, String markdown) {
      log.entry(markdownService, markdown);
      return log.exit(new MarkdownContent(markdownService, markdown));
    }
}