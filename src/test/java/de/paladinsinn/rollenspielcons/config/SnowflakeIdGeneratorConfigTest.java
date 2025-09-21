package de.paladinsinn.rollenspielcons.config;


import cn.ipokerface.snowflake.SnowflakeIdGenerator;
import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 *
 * @author klenkes74
 * @since 21.09.25
 */
@XSlf4j
public class SnowflakeIdGeneratorConfigTest {
  private static final long WORKER_ID = 0;
  private static final long DATA_CENTER_ID = 0;
  
  
  private SnowflakeIdGenerator generator;
  
  
  @BeforeEach
  public void before() {
    SnowflakeIdGeneratorConfig sut = new SnowflakeIdGeneratorConfig(DATA_CENTER_ID, WORKER_ID);
    generator = sut.generator();
  }
 
  
  @Test
  public void shouldBeAValidSnowflakeIdGenerator() {
    log.entry();
    
    assertEquals(SnowflakeIdGenerator.class.getCanonicalName(), generator.getClass().getCanonicalName());

    log.exit();
  }
  
  
  @Test
  public void shouldThrowExceptionWhenDatacenterIdIsLessThanZero() {
    log.entry();
    
    IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> new SnowflakeIdGeneratorConfig(-1, WORKER_ID));
    
    assertEquals("Datacenter-ID must be between 0 and 31 (-1).", result.getMessage());
    
    log.exit();
  }
  
  
  @Test
  public void shouldThrowExceptionWhenDatacenterIdIsMoreThan31() {
    log.entry();
    
    IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> new SnowflakeIdGeneratorConfig(32, WORKER_ID));
    
    assertEquals("Datacenter-ID must be between 0 and 31 (32).", result.getMessage());

    log.exit();
  }
  
  
  @Test
  public void shouldThrowExceptionWhenMachineIdIsLessThanZero() {
    log.entry();
    
    IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> new SnowflakeIdGeneratorConfig(DATA_CENTER_ID, -1));
    
    assertEquals("Machine-ID must be between 0 and 31 (-1).", result.getMessage());
    
    log.exit();
  }
  
  
  @Test
  public void shouldThrowExceptionWhenMachineIdIsMoreThan31() {
    log.entry();
    
    IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> new SnowflakeIdGeneratorConfig(DATA_CENTER_ID, 32));
    
    assertEquals("Machine-ID must be between 0 and 31 (32).", result.getMessage());
    
    log.exit();
  }
  
  
  @Test
  public void shouldWorkTheSpringWayWhenConfiguredCorrectly() {
    log.entry();
    
    SnowflakeIdGeneratorConfig config = new SnowflakeIdGeneratorConfig();
    SnowflakeIdGenerator result = config.generator();
    
    assertEquals(SnowflakeIdGenerator.class.getCanonicalName(), result.getClass().getCanonicalName());
    
    log.exit();
  }
}
