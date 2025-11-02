package de.paladinsinn.rollenspielcons.domain.model.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Slf4j
public class PhysicalAddressTest {
  private static final Double LONGITUTE = 49.123456;
  private static final Double LATITUDE = 10.123456;
  private static final String ADDRESS = "Hauptstr. 1, 12345 Musterstadt";
  private static final String THREE_WORDS = "word1.word2.word3";
  
  private static final PhysicalAddress SUT = PhysicalAddressImpl.builder()
                                                                .address(ADDRESS)
                                                                .latitude(LATITUDE)
                                                                .longitude(LONGITUTE)
                                                                .threeWords(THREE_WORDS)
                                                                .build();
  
  @Test
  public void shouldReturnUriToOpenstreetmap() {
    log.trace("enter - {}", new Object[] {LONGITUTE, LATITUDE});
    
    String result = SUT.getUri();
    
    assertEquals("https://www.openstreetmap.org/#map=19/49.123456/10.123456", result);
    
    log.trace("exit - {}", new Object[] {result});
  }
  
  @Test
  public void shouldReturnAddressAsDisplaytext() {
    log.trace("enter - {}", new Object[] {ADDRESS});
    
    String result = SUT.getDisplayText();
    
    assertEquals(ADDRESS, result);
    
    log.trace("exit - {}", new Object[] {result});
  }
  
  @Test
  public void shouldReturnAddressAndUriAsEmailText() {
    log.trace("enter - {}", new Object[] {ADDRESS});
    
    String result = SUT.getEmailText();
    
    assertEquals(ADDRESS + " (" + SUT.getUri() + ")", result);
    
    log.trace("exit - {}", new Object[] {result});
  }
  
}
