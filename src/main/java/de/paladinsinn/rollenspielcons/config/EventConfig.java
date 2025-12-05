/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
