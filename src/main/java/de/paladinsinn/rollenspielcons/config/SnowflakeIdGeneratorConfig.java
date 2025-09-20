package de.paladinsinn.rollenspielcons.config;


import cn.ipokerface.snowflake.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Configuration
public class SnowflakeIdGeneratorConfig {
  
  @Value("${snowflake.datacenter-id:1}")
  private long datacenterId;
  
  @Value("${snowflake.machine-id:1}")
  private long machineId;
  
  @Bean
  public SnowflakeIdGenerator snowflakeIdGenerator() {
    return new SnowflakeIdGenerator(datacenterId, machineId);
  }
}
