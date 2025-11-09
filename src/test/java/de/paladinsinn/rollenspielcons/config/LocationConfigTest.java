package de.paladinsinn.rollenspielcons.config;


import com.what3words.javawrapper.What3WordsV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 *
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Slf4j
public class LocationConfigTest {
  
  @Test
  public void shouldReturnWhat3WordsInstanceIfApiKeyIsSet() {
    log.trace("enter - ");
    
    LocationConfig config = new LocationConfig();
    What3WordsV3 result = config.what3WordsV3("SOME-API-KEY", "http://api.what3words.com/v3/");
    
    assertNotNull(result);
    assertEquals(What3WordsV3.class, result.getClass());
    
    log.trace("exit - ");
  }
}
