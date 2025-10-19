package de.paladinsinn.rollenspielcons.persistence;


import de.paladinsinn.rollenspielcons.config.SnowflakeIdGeneratorConfig;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * A provider for the {@link cn.ipokerface.snowflake.SnowflakeIdGenerator} to
 * be used in entities.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@Component
@XSlf4j
public class SnowflakeIdGenerator implements ApplicationContextAware {
  /**
   * The context to be used for injecting the id generator.
   */
  private static cn.ipokerface.snowflake.SnowflakeIdGenerator generator;
  
  @Override
  public void setApplicationContext(final ApplicationContext context)
      throws BeansException {
    log.entry(context);
    
    generator = context.getBean(SnowflakeIdGeneratorConfig.class).generator();
    
    log.exit();
  }
  
  /**
   * Generates a new id.
   *
   * @return the new id.
   */
  public static long generateId() {
    log.entry();
    
    return log.exit(generator.nextId());
  }
}
