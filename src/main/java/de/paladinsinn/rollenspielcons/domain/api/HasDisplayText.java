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

package de.paladinsinn.rollenspielcons.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The text to be displayed for an entity.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
public interface HasDisplayText {
  /**
   * Get the text to be displayed for this entity.
   *
   * @return the display text
   */
  String getDisplayText();
  
  /**
   * Get the URI for <a href="https://wwww.openstreetmap.org">OpenStreetMap</a> for this entity.
   *
   * @return the URI
   */
  String getUri();
  
  /**
   * Get the text to be used in emails for this entity.
   *
   * @return the email text
   */
  @JsonIgnore
  default String getEmailText() {
    return getDisplayText() + " (" + getUri() + ")";
  }
  
  /**
   * Get a HTML link for this entity.
   *
   * @return the HTML link
   */
  @JsonIgnore
  default String getHtmlLink() {
    return "<a href=\"" + getUri() + "\">" + getDisplayText() + "</a>";
  }
  
  /**
   * Get a Markdown link for this entity.
   *
   * @return the Markdown link
   */
  @JsonIgnore
  default String getMarkdownLink() {
    return "[" + getDisplayText() + "](" + getUri() + ")";
  }
  
  /**
   * Get an AsciiDoc link for this entity.
   *
   * @return the AsciiDoc link
   */
  @JsonIgnore
  default String getAsciiDocLink() {
    return "link:" + getUri() + "[" + getDisplayText() + "]";
  }
}
