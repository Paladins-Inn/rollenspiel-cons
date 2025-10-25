package de.paladinsinn.rollenspielcons.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;
import lombok.extern.slf4j.XSlf4j;
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
@XSlf4j
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    log.entry();
    CaffeineCacheManager cm = new CaffeineCacheManager();
    cm.setCaffeine(
        Caffeine.newBuilder()
                .maximumSize(500)                       // anpassen
                .expireAfterWrite(Duration.ofMinutes(60)) // TTL anpassen
    );
    
    return log.exit(cm);
  }

  @Bean("contentHashKeyGenerator")
  public KeyGenerator contentHashKeyGenerator() {
    return (target, method, params) -> {
      log.entry(target.getClass().getSimpleName(), method.getName(), params.length);
      
      if (params.length == 0 || params[0] == null) return log.exit("null");
      
      try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(((String) params[0]).getBytes(StandardCharsets.UTF_8));
        return log.exit(HexFormat.of().formatHex(digest));
      } catch (Exception e) {
        // fallback: use string hash
        return log.exit(Integer.toHexString(params[0].hashCode()));
      }
    };
  }
}