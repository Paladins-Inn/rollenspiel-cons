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


import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
@NoArgsConstructor
@Getter
@ToString()
@EqualsAndHashCode(of = {"id"})
@Slf4j
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
