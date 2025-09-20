package de.paladinsinn.rollenspielcons.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class ManagementSecurityConfiguration {

    @Value("${spring.security.user.name:admin}")
    private String username;

    @Value("${spring.security.user.password:admin123}")
    private String password;

    // Nur Management-Endpoints konfigurieren (Order 1 = höchste Priorität)
    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
                .anyRequest().hasRole("ACTUATOR")
            )
            .httpBasic(httpBasic -> {})
            .userDetailsService(actuatorUserDetailsService())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService actuatorUserDetailsService() {
        UserDetails user = User.builder()
            .username(username)
            .password(passwordEncoder().encode(password))
            .roles("ACTUATOR")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
