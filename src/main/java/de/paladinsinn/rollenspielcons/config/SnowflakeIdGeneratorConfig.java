package de.paladinsinn.rollenspielcons.config;


import cn.ipokerface.snowflake.SnowflakeIdGenerator;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provider for a <a href="https://en.wikipedia.org/wiki/Snowflake_ID">Snowflake ID</a> generator.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Configuration
@NoArgsConstructor
@XSlf4j
public class SnowflakeIdGeneratorConfig {
  private static final long LOWER_BOUND_FOR_DATACENTER_AND_WORKER_ID = 0L;
  private static final long UPPER_BOUND_FOR_DATACENTER_AND_WORKER_ID = 31L;
  
  
  /**
   * Creates a new {@link SnowflakeIdGenerator}.
   * @return The new {@link SnowflakeIdGenerator}.
   */
  @Bean
  public SnowflakeIdGenerator generator() {
    return log.exit(new SnowflakeIdGenerator(datacenterId, machineId));
  }
  
  @Value("${snowflake.datacenter-id:0}")
  private long datacenterId;
  
  @Value("${snowflake.machine-id:0}")
  private long machineId;
  
  
  //just for testing
  SnowflakeIdGeneratorConfig(final long datacenterId, final long machineId) {
    log.entry(datacenterId, machineId);
    
    this.datacenterId = datacenterId;
    this.machineId = machineId;
    
    init();
    
    log.exit();
  }
  
  
  @PostConstruct
  public void init() {
    log.entry();
    
    validateDatacenterId(datacenterId);
    validateMachineId(machineId);
    
    log.exit();
  }
  
  private static void validateDatacenterId(final long datacenterId) {
    validateId("Datacenter-ID", datacenterId);
  }
  
  private static void validateMachineId(final long machineId) {
    validateId("Machine-ID", machineId);
  }
  
  private static void validateId(final String idName, final long id) {
    if (id < LOWER_BOUND_FOR_DATACENTER_AND_WORKER_ID || id > UPPER_BOUND_FOR_DATACENTER_AND_WORKER_ID) {
      log.error("{} must be between {} and {}. id={}", idName, LOWER_BOUND_FOR_DATACENTER_AND_WORKER_ID, UPPER_BOUND_FOR_DATACENTER_AND_WORKER_ID, id);
      
      throw new IllegalArgumentException(idName + " must be between " + LOWER_BOUND_FOR_DATACENTER_AND_WORKER_ID + " and " + UPPER_BOUND_FOR_DATACENTER_AND_WORKER_ID + " (" + id + ").");
    }
    
    log.trace("{} is valid. id={}", idName, id);
  }
}