package de.paladinsinn.rollenspielcons.config;


import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 *
 *
 * @author klenkes74
 * @since 20.09.25
 */
@Configuration
@EnableAspectJAutoProxy
@XSlf4j
public class MetricsConfig {
  
  @Bean
  public CountedAspect countedAspect(MeterRegistry registry) {
    log.entry(registry);
    
    return log.exit(new CountedAspect(registry));
  }
  
  @Bean
  public TimedAspect timedAspect(MeterRegistry registry) {
    log.entry(registry);
    
    return log.exit(new TimedAspect(registry));
  }
}
