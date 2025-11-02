package de.paladinsinn.rollenspielcons.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

@Configuration
@Slf4j
public class ManagementSecurityConfiguration {

    @Value("${spring.security.user.name:admin}")
    private String username;

    @Value("${spring.security.user.password:./.}")
    private String password;

    // Nur Management-Endpoints konfigurieren (Order 1 = höchste Priorität)
    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
      log.trace("enter -  {}", http);
      
        http
            .securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
                .anyRequest().hasRole("ACTUATOR")
            )
            .httpBasic(httpBasic -> {})
            .userDetailsService(actuatorUserDetailsService())
            .csrf(AbstractHttpConfigurer::disable);

        SecurityFilterChain result = http.build();
        
        log.trace("exit - {}", result);
        return result;
    }

    @Bean
    public UserDetailsService actuatorUserDetailsService() {
      log.trace("enter - ");
      
      generateRandomPasswordWhenNeeded();
      
      UserDetails user = User.builder()
          .username(username)
          .password(passwordEncoder().encode(password))
          .roles("ACTUATOR")
          .build();
      
      UserDetailsService result = new InMemoryUserDetailsManager(user);
      
      log.trace("exit - {}", result);
      return result;
    }
    
    private void generateRandomPasswordWhenNeeded() {
      log.trace("enter -  {}", password);

      if ("./.".equals(password)) {
        password = UUID.randomUUID().toString();
        
        log.info("No password for actuator user configured. Generated password: {}", password);
      }
      
      log.trace("exit - {}", new Object[] {password});
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
      log.trace("enter - ");
      
      PasswordEncoder result = new BCryptPasswordEncoder();
      log.trace("exit - {}", result);
      return result;
    }
}
