package de.paladinsinn.rollenspielcons.config;


import com.what3words.javawrapper.What3WordsV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Slf4j
public class LocationConfigTest {
  
  @Test
  public void shouldComplainAboutMissingApiKeyIfApiKeyIsMissing() {
    log.trace("enter - ");
    
    LocationConfig config = new LocationConfig();
    
    assertThrows(IllegalStateException.class, config::checkWhat3WordsApiKey);
    
    log.trace("exit - ");
  }
  
  @Test
  public void shouldComplainAboutMissingApiKeyIfApiKeyIsBlank() {
    log.trace("enter - ");
    
    @SuppressWarnings("deprecation") // using deprecated constructor for testing only
    LocationConfig config = new LocationConfig("   ");
    
    assertThrows(IllegalStateException.class, config::checkWhat3WordsApiKey);
    
    log.trace("exit - ");
  }
  
  
  @Test
  public void shouldReturnWhat3WordsInstanceIfApiKeyIsSet() {
    log.trace("enter - ");
    
    @SuppressWarnings("deprecation") // using deprecated constructor for testing only
    LocationConfig config = new LocationConfig("SOME-API-KEY");
    What3WordsV3 result = config.what3words();
    
    assertNotNull(result);
    assertEquals(What3WordsV3.class, result.getClass());
    
    log.trace("exit - ");
  }
}
