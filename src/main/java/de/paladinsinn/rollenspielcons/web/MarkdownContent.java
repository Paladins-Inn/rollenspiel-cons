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