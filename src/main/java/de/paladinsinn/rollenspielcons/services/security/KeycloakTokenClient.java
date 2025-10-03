package de.paladinsinn.rollenspielcons.services.security;


import de.paladinsinn.rollenspielcons.services.security.model.TokenResponse;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@XSlf4j
public class KeycloakTokenClient {
  private final WebClient.Builder webClientBuilder;
  
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String protectionApiUrl;
  
  @Value("${spring.security.oauth2.client.registration.sso.client-id}")
  private String clientId;
  
  @Value("${spring.security.oauth2.client.registration.sso.client-secret}")
  private String clientSecret;
  
  
  private WebClient webClient;
  
  @PostConstruct
  public void init() {
    log.entry(webClientBuilder, protectionApiUrl);
    
    webClient = webClientBuilder.baseUrl(protectionApiUrl + "/protocol/openid-connect/token")
                                .defaultHeader("Accept", "application/json")
                                .build();
    
    log.exit(webClient);
  }
  
  /**
   * Holt ein PAT (Protection API Token) über Client Credentials.
   */
  public Mono<String> getProtectionApiToken() {
    log.entry();
    
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "client_credentials");
    // scope ist nicht immer nötig, schadet aber nicht:
    form.add("scope", "uma_protection");
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    
    return log.exit(webClient
                        .post()
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .bodyValue(form)
                        .retrieve()
                        .bodyToMono(TokenResponse.class)
                        .map(TokenResponse::getAccessToken));
  }
  
  /**
   * (Optional) Tauscht ein Permission-Ticket gegen ein RPT. Normalerweise macht das der Client
   * (z. B. SPA).
   */
  @SuppressWarnings("unused")
  public Mono<String> exchangeUmaTicketForRpt(String ticket, String audience) {
    log.entry();
    
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "urn:ietf:params:oauth:grant-type:uma-ticket");
    form.add("ticket", ticket);
    form.add("audience", audience); // = dein RS-Client (z. B. my-api)
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    
    return log.exit(webClient.post()
                             .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                             .bodyValue(form)
                             .retrieve()
                             .bodyToMono(TokenResponse.class)
                             .map(TokenResponse::getAccessToken)); // das RPT
  }
}
