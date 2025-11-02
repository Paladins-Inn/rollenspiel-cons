package de.paladinsinn.rollenspielcons.domain;


import de.paladinsinn.rollenspielcons.domain.api.AbstractBaseEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Slf4j
public class BaseEventTest {
  @Test
  public void shouldReturnAValidUUIDWhenNoWasGivenDuringCreation() {
    log.trace("enter - ");
    
    BaseEvent event = BaseEvent.builder()
        .data("Some data")
        .build();
    log.info("Created event: {}", event);
    
    assertNotNull(event.getId(), "ID should be set");
    
    log.trace("exit - ");
  }
  
  
  @SuperBuilder(toBuilder = true)
  @Getter
  @ToString(callSuper = true)
  @EqualsAndHashCode(callSuper = true)
  private static class BaseEvent extends AbstractBaseEvent {
    private final String data;
  }
}
