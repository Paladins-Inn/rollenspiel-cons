package de.paladinsinn.rollenspielcons.domain.model;

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
  default String getEmailText() {
    return getDisplayText() + " (" + getUri() + ")";
  }
  
  /**
   * Get a HTML link for this entity.
   *
   * @return the HTML link
   */
  default String getHtmlLink() {
    return "<a href=\"" + getUri() + "\">" + getDisplayText() + "</a>";
  }
  
  /**
   * Get a Markdown link for this entity.
   *
   * @return the Markdown link
   */
  default String getMarkdownLink() {
    return "[" + getDisplayText() + "](" + getUri() + ")";
  }
  
  /**
   * Get an AsciiDoc link for this entity.
   *
   * @return the AsciiDoc link
   */
  default String getAsciiDocLink() {
    return "link:" + getUri() + "[" + getDisplayText() + "]";
  }
}
