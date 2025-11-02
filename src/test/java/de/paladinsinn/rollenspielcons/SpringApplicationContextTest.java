package de.paladinsinn.rollenspielcons;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-10
 */
@SpringBootTest(classes = Application.class)
@ActiveProfiles({"test","local"})
@Slf4j
public class SpringApplicationContextTest {
  @Test
  public void contextLoads() {
  }
}
