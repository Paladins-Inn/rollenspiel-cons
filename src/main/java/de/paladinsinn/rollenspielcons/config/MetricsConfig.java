package de.paladinsinn.rollenspielcons.config;


import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MetricsConfig {
  
  @Bean
  public CountedAspect countedAspect(MeterRegistry registry) {
    log.trace("enter -  {}", registry);
    
    var result = new CountedAspect(registry);
    
    log.trace("exit - {}", result);
    return result;
  }
  
  @Bean
  public TimedAspect timedAspect(MeterRegistry registry) {
    log.trace("enter -  {}", registry);
    
    var result = new TimedAspect(registry);

    log.trace("exit - {}", result);
    return result;
  }
}
