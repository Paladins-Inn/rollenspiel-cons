package de.paladinsinn.rollenspielcons.domain.model.time;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
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
public class TimeSpecTest {
  private static final long DURATION_IN_SECONDS = 3600*6 - 1;
  private static final Duration DURATION = Duration.ofSeconds(DURATION_IN_SECONDS);

  private static final TimeSpecImpl SUT = TimeSpecImpl.builder()
                                                      .startDay(ZonedDateTime.parse("2024-12-24T18:00:00+01:00[Europe/Berlin]"))
                                                      .duration(DURATION)
                                                      .build();
  private static final TimeSpecImpl SUT_WITHOUT_DURATION = TimeSpecImpl.builder()
                                                                       .startDay(ZonedDateTime.parse("2024-12-24T18:00:00+01:00[Europe/Berlin]"))
                                                                       .build();
  private static final LocalDateTime LOCAL_START = LocalDateTime.of(2024, 12, 24, 18, 0, 0);
  private static final LocalDateTime LOCAL_END = LocalDateTime.of(2024, 12, 24, 23, 59, 59);
  private static final LocalDateTime LOCAL_END_WITHOUT_DURATION = LocalDateTime
      .of(2024, 12, 24, 18, 0, 0)
     .toLocalDate().atTime(LocalTime.MAX);
  
  @Test
  public void shouldReturnTheCorrectStartWhenADurationIsSet() {
    log.trace("enter - {}", new Object[] {SUT, LOCAL_START});

    assertEquals(LOCAL_START, SUT.getStart());
    
    log.trace("exit - ");
  }
  
  @Test
  public void shouldReturnTheCorrectEndWhenADurationIsSet() {
    log.trace("enter - {}", new Object[] {SUT, LOCAL_END});
    
    assertEquals(LOCAL_END, SUT.getEnd());
    
    log.trace("exit - ");
  }
  
  
  @Test
  public void shouldReturnTheCorrectStartWhenNoDurationIsSet() {
    log.trace("enter - {}", new Object[] {SUT_WITHOUT_DURATION, LOCAL_START});
    
    assertEquals(LOCAL_START, SUT_WITHOUT_DURATION.getStart());
    
    log.trace("exit - ");
  }
  
  @Test
  public void shouldReturnTheCorrectEndWhenNoDurationIsSet() {
    log.trace("enter - {}", new Object[] {SUT_WITHOUT_DURATION, LOCAL_END_WITHOUT_DURATION});
    
    assertEquals(LOCAL_END_WITHOUT_DURATION, SUT_WITHOUT_DURATION.getEnd());
    
    log.trace("exit - ");
  }
}
