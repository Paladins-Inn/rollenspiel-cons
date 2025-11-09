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
