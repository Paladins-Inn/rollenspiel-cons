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

package de.paladinsinn.rollenspielcons.config;


import com.what3words.javawrapper.What3WordsV3;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * This is a provider for the {@link What3WordsV3} java wrapper to the <a href="https://what3words.com/">What3Words</a> API.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Configuration
@NoArgsConstructor
@Slf4j
public class LocationConfig {

  @Bean
  @Primary
  public What3WordsV3 what3WordsV3(
      @Value("${what3words.api.key:changeme}") final String apiKey,
      @Value("${what3words.api.url:http://api.what3words.com/v3/}") final String url
  ) {
    log.trace("enter - {}, {}", apiKey, url);
    
    if ("changeme".equals(apiKey)) {
      log.warn("No What3Words API key configured! Please set the 'what3words.api.key' property.");
      throw new IllegalStateException("What3Words API key is not configured.");
    }
    
    What3WordsV3 result = new What3WordsV3(apiKey, url);

    log.trace("exit - {}", result);
    return result;
  }
}
