package de.paladinsinn.rollenspielcons.config;


import com.what3words.javawrapper.What3WordsV3;
import jakarta.annotation.PostConstruct;
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
  @Value("${what3words.api.key:changeme}")
  private String what3wordsApiKey;

  @PostConstruct
  public void checkWhat3WordsApiKey() {
    log.trace("enter - {}", what3wordsApiKey);
    
    if (what3wordsApiKey == null || what3wordsApiKey.isBlank()) {
      log.warn("No What3Words API key configured! Please set the 'what3words.api.key' property.");
      throw new IllegalStateException("What3Words API key is not configured.");
    } else {
      log.info("What3Words API key is configured.");
    }
  }
  
  @Bean
  @Primary
  public What3WordsV3 what3WordsV3(
      @Value("${what3words.api.key}") final String apiKey,
      @Value("${what3words.api.url:http://api.what3words.com/v3/}") final String url
  ) {
    log.trace("enter - {}, {}", apiKey, url);
    
    What3WordsV3 result = new What3WordsV3(apiKey, url);

    log.trace("exit - {}", result);
    return result;
  }
  

  // only for testing, deprecating it as warning.
  @SuppressWarnings("DeprecatedIsStillUsed")
  @Deprecated
  LocationConfig(final String what3wordsApiKey) {
    log.trace("enter -  {}", what3wordsApiKey);
    
    this.what3wordsApiKey = what3wordsApiKey;
    
    log.trace("exit - {}", new Object[] {what3wordsApiKey});
  }
}
