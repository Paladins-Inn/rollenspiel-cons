package de.paladinsinn.rollenspielcons;


import lombok.ToString;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-06
 */
@SpringBootApplication
@ToString
@XSlf4j
public class RollenspielConsApplication {
  public static void main(String[] args) {
    log.entry();
    
    SpringApplication.run(RollenspielConsApplication.class, args);
    
    log.exit();
  }
}