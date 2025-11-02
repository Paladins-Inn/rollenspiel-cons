package de.paladinsinn.rollenspielcons.web;


import gg.jte.Content;
import gg.jte.TemplateOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */

@RequiredArgsConstructor
@Slf4j
public class MarkdownContent implements Content {
    private final MarkdownService markdownService;
    private final String markdown;

    @Override
    public void writeTo(TemplateOutput output) {
      log.trace("enter -  {}", output);
      
      output.writeContent(markdownService.toHtml(markdown));
      
      log.trace("Exiting method");
    }

    public static MarkdownContent of(MarkdownService markdownService, String markdown) {
      log.trace("enter -  {}, {}", markdownService, markdown);
      
      var result = new MarkdownContent(markdownService, markdown);

      log.trace("exit - {}", result);
      return result;
    }
}