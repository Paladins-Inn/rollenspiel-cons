
package de.paladinsinn.rollenspielcons.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@XSlf4j
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      log.entry(http);
        http
            .authorizeHttpRequests(authz -> authz
                // Öffentliche Bereiche (ohne Authentifizierung)
                .requestMatchers("/", "/home", "/legalese**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/assets/**", "/images/**").permitAll()
                .requestMatchers("/*.html", "/favicon.ico").permitAll()
                
                // Authentication endpoints
                .requestMatchers("/login**", "/error**", "/oauth2/**").permitAll()
                
                // Geschützte Bereiche (Authentifizierung erforderlich)
                .requestMatchers("/dashboard", "/dashboard/**").authenticated()
                .requestMatchers("/profile", "/profile/**").authenticated()
                .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                
                .requestMatchers("/api/events**").permitAll()
                .requestMatchers("/api/**").authenticated()
                
                // Standard: Alle anderen Requests sind öffentlich
                .anyRequest().permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/actuator/**")
            )
        ;

        return log.exit(http.build());
    }
  
  @SuppressWarnings("unchecked")
  @Bean
  JwtAuthenticationConverter jwtAuthConverter() {
    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwt -> {
      var authorities = new ArrayList<GrantedAuthority>();
      
      // Realm-Rollen (optional)
      var realmRoles = (Map<String, Object>) jwt.getClaim("realm_access");
      if (realmRoles != null && realmRoles.get("roles") instanceof List<?> roles) {
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r)));
      }
      
      // UMA permissions -> PERM:{resourceName}#{scope}
      var perms = (List<Map<String, Object>>) jwt.getClaims().get("permissions");
      if (perms != null) {
        for (var p : perms) {
          var rs     = (String) p.getOrDefault("rsname", p.get("rsid"));
          var scopes = (List<String>) p.get("scopes");
          if (scopes != null) {
            for (var s : scopes) {
              authorities.add(new SimpleGrantedAuthority("PERM:" + rs + "#" + s));
            }
          }
        }
      }
      return authorities;
    });
    return converter;
  }
  
}
