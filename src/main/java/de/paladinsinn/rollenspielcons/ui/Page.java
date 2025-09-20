package de.paladinsinn.rollenspielcons.ui;


import lombok.*;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;

import java.security.Principal;
import java.util.UUID;


/**
 *
 *
 * @author klenkes74
 * @since 07.09.25
 */
@Builder(toBuilder = true, setterPrefix = "")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(of = {"id"})
@XSlf4j
public class Page {
  @Builder.Default
  private UUID id = UUID.randomUUID();
  
  private String title;
  private String description;
  private String author;
  
  private Principal principal;
  
  public String getUserName() {
    return principal != null ? principal.getName() : "- not logged in -";
  }
  
  public String getContextPath(@Value("${server.servlet.context-path:./}") final String contextPath) {
    return contextPath;
  }
}
