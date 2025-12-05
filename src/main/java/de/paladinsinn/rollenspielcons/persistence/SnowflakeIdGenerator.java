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

package de.paladinsinn.rollenspielcons.persistence;


import de.paladinsinn.rollenspielcons.config.SnowflakeIdGeneratorConfig;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SnowflakeIdGenerator implements ApplicationContextAware {
  /**
   * The context to be used for injecting the id generator.
   */
  private static cn.ipokerface.snowflake.SnowflakeIdGenerator generator;
  
  @Override
  public void setApplicationContext(final ApplicationContext context)
      throws BeansException {
    log.trace("enter -  {}", context);
    
    generator = context.getBean(SnowflakeIdGeneratorConfig.class).generator();
    
    log.trace("Exiting method");
  }
  
  /**
   * Generates a new id.
   *
   * @return the new id.
   */
  public static long generateId() {
    log.trace("enter - ");
    
    var result = generator.nextId();

    log.trace("exit - {}", result);
    return result;
  }
}
