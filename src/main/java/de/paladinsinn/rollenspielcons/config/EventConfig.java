package de.paladinsinn.rollenspielcons.config;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;


/**
 * Configures the spring event bus handling.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@Configuration
@Slf4j
public class EventConfig {
  @Bean
  public Executor eventTaskExecutor() {
    return Executors.newFixedThreadPool(4);
  }

  @Bean
  public ApplicationEventMulticaster applicationEventMulticaster(Executor eventTaskExecutor) {
    log.trace("Creating application event multicaster with executor: {}", eventTaskExecutor);
    
    SimpleApplicationEventMulticaster mic = new SimpleApplicationEventMulticaster();
    
    mic.setTaskExecutor(eventTaskExecutor); // macht Listener async
    mic.setErrorHandler(t -> {
      if (t instanceof RuntimeException) {
        throw (RuntimeException) t;
      } else if (t instanceof Error) {
        throw (Error) t;
      } else {
        throw new RuntimeException(t);
      }
    });
    
    log.trace("Application event multicaster created: {}", mic);
    return mic;
  }
}
