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


import com.github.benmanes.caffeine.cache.Caffeine;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * The configuration for caching.
 *
 * <p>Mostly used for MarkDown caching.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-25
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    log.trace("Creating cache manager");
    CaffeineCacheManager cm = new CaffeineCacheManager();
    cm.setCaffeine(
        Caffeine.newBuilder()
                .maximumSize(500)                       // anpassen
                .expireAfterWrite(Duration.ofMinutes(60)) // TTL anpassen
    );
    
    log.trace("Cache manager created: {}", cm);
    return cm;
  }

  @Bean("contentHashKeyGenerator")
  public KeyGenerator contentHashKeyGenerator() {
    return (target, method, params) -> {
      log.trace("Generating cache key for {}.{} with {} params", target.getClass().getSimpleName(), method.getName(), params.length);
      
      if (params.length == 0 || params[0] == null) {
        log.trace("Returning null key");
        return "null";
      }
      
      try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(((String) params[0]).getBytes(StandardCharsets.UTF_8));
        String hash = HexFormat.of().formatHex(digest);
        log.trace("Generated hash key: {}", hash);
        return hash;
      } catch (Exception e) {
        // fallback: use string hash
        String hash = Integer.toHexString(params[0].hashCode());
        log.trace("Generated fallback hash key: {}", hash);
        return hash;
      }
    };
  }
}