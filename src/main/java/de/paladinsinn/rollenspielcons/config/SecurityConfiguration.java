
package de.paladinsinn.rollenspielcons.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {
  
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri:-}")
  private String issuerUri;
  
  @Value("${ingress.host:localhost:-}")
  private String appUrl;
  
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.trace("enter -  {}", http);
    
    http
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/")
            .defaultSuccessUrl("/dashboard", true)
            .failureUrl("/login?error=true")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessHandler(ssoLogoutHandler())
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        )
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/actuator/**")
        )
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/home", "/legalese**").permitAll()
            .requestMatchers("/css/**", "/js/**", "/assets/**", "/images/**").permitAll()
            .requestMatchers("/*.html", "/favicon.ico").permitAll()
            
            // Authentication endpoints
            .requestMatchers("/login**", "/logout**", "/error**", "/oauth2/**").permitAll()
            
            // Geschützte Bereiche (Authentifizierung erforderlich)
            .requestMatchers("/dashboard", "/dashboard/**").authenticated()
            .requestMatchers("/profile", "/profile/**").authenticated()
            .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
            
            .requestMatchers(HttpMethod.GET, "/**").permitAll()
            .requestMatchers(HttpMethod.HEAD, "/**").permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            
            .requestMatchers(HttpMethod.POST, "/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/**").authenticated()
            .requestMatchers(HttpMethod.PATCH, "/**").authenticated()
        )
    ;
    
    var result = http.build();
    
    log.trace("exit - {}", result);
    return result;
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
  
  @Bean
  public LogoutSuccessHandler ssoLogoutHandler() {
    return (request, response, authentication) -> {
      log.trace("enter -  {}, {}, {}", request, response, authentication);
      String result = issuerUri + "/protocol/openid-connect/logout?redirect_uri=" + appUrl;
      response.sendRedirect(result);
      log.trace("exit - {}", new Object[] {result});
    };
  }
}
