package de.paladinsinn.rollenspielcons.domain.model;


import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@XSlf4j
public class DisplayableNameTest {
  private static final String NAME = "A Displayable Name";
  private static final String URI = "https://www.paladins-inn.de";
  
  @Test
  public void shouldReturnTheNameAsDisplayText() {
    log.entry(NAME, URI);
    
    DisplayableName sut = DisplayableName.builder()
        .name(NAME)
        .uri(URI)
        .build();
    
    assertEquals(NAME, sut.getDisplayText());
    
    log.exit(sut.getName());
  }
}
