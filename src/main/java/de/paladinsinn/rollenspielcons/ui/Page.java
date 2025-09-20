package de.paladinsinn.rollenspielcons.ui;


import lombok.*;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;


/**
 *
 *
 * @author klenkes74
 * @since 07.09.25
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString()
@EqualsAndHashCode(of = {"id"})
@XSlf4j
public class Page {
  @Builder.Default
  private UUID id = UUID.randomUUID();
  
  private String title;
  private String description;
  private String author;
  
  private OAuth2User user;
  
  public boolean isLoggedIn() {
    return user != null;
  }
  
  public String getUserName() {
    return user != null ? user.getName() : "- not logged in -";
  }
  
  public String getEmail() {
    return user != null ? user.getAttribute("email") : "./.";
  }
  
  public String getDiscordId() {
    return user != null ? user.getAttribute("id") : "./.";
  }
}
