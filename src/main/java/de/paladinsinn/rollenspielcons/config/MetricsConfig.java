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
