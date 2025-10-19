package de.paladinsinn.rollenspielcons;


import de.paladinsinn.rollenspielcons.persistence.AbstractBaseEntity;
import lombok.ToString;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * The spring application.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-06
 */
@SpringBootApplication(exclude =  { WebFluxAutoConfiguration.class })
@EntityScan(basePackageClasses = { AbstractBaseEntity.class })
@EnableWebSecurity
@ToString
@XSlf4j
public class Application {
  public static void main(String[] args) {
    log.entry();
    
    SpringApplication.run(Application.class, args);
    
    log.exit();
  }
}