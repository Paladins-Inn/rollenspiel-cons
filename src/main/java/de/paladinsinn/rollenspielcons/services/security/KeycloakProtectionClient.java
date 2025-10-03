package de.paladinsinn.rollenspielcons.services.security;

import de.paladinsinn.rollenspielcons.services.security.model.PermissionRequest;
import de.paladinsinn.rollenspielcons.services.security.model.PermissionTicketResponse;
import de.paladinsinn.rollenspielcons.services.security.model.ResourceRepresentation;
import de.paladinsinn.rollenspielcons.services.security.model.ResourceResponse;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@XSlf4j
public class KeycloakProtectionClient {
  private final WebClient.Builder webClientBuilder;
  private final KeycloakTokenClient tokens;
  
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String protectionApiUrl;
  
  private WebClient protectionClient;
  
  
  @PostConstruct
  public void init() {
    log.entry(webClientBuilder, protectionApiUrl);
    
    protectionClient = webClientBuilder.baseUrl(protectionApiUrl)
                                       .defaultHeader("Accept", "application/json")
                                       .build();
    
    log.exit(protectionClient);
  }
  
  
  private WebClient withBearer(WebClient wc, String token) {
    log.entry(wc, token);
    
    return log.exit(wc.mutate()
                      .defaultHeaders(h -> h.setBearerAuth(token))
                      .build());
  }
  
  /**
   * Resource registrieren -> liefert ResourceResponse mit id
   */
  @SuppressWarnings("unused")
  public ResourceResponse createResource(ResourceRepresentation res) {
    log.entry(res);
    
    return log.exit(tokens.getProtectionApiToken()
                          .flatMap(pat -> withBearer(protectionClient, pat)
                              .post()
                              .uri("/resource_set")
                              .contentType(MediaType.APPLICATION_JSON)
                              .bodyValue(res)
                              .retrieve()
                              .bodyToMono(ResourceResponse.class))
                          .block());
  }
  
  /**
   * Resource auslesen
   */
  @SuppressWarnings("unused")
  public ResourceResponse getResource(String resourceId) {
    log.entry(resourceId);
    
    return log.exit(tokens.getProtectionApiToken()
                          .flatMap(pat -> withBearer(protectionClient, pat)
                              .get()
                              .uri("/resource_set/{id}", resourceId)
                              .retrieve()
                              .bodyToMono(ResourceResponse.class))
                          .block());
  }
  
  /**
   * Resource löschen
   */
  @SuppressWarnings("unused")
  public void deleteResource(String resourceId) {
    log.entry(resourceId);
    
    tokens.getProtectionApiToken()
          .flatMap(pat -> withBearer(protectionClient, pat)
              .delete()
              .uri("/resource_set/{id}", resourceId)
              .retrieve()
              .bodyToMono(Void.class)).block()
    ;
    
    log.exit();
  }
  
  /**
   * Ressourcen des aufrufenden RS-Clients auflisten (optional paginiert)
   */
  @SuppressWarnings("unused")
  public List<ResourceResponse> listResources() {
    log.entry();
    
    return log.exit(tokens.getProtectionApiToken()
                          .flatMapMany(pat -> withBearer(protectionClient, pat)
                              .get()
                              .uri(uriBuilder -> uriBuilder.path("/resource_set").build())
                              .retrieve()
                              .bodyToFlux(ResourceResponse.class)).toStream().toList());
  }
  
  /**
   * Permission Ticket für eine oder mehrere Anfragen erzeugen
   */
  @SuppressWarnings("unused")
  public PermissionTicketResponse createPermissionTicket(List<PermissionRequest> requests) {
    log.entry(requests);
    
    return log.exit(tokens.getProtectionApiToken()
                          .flatMap(pat -> withBearer(protectionClient, pat)
                              .post()
                              .uri("/permission")
                              .contentType(MediaType.APPLICATION_JSON)
                              .bodyValue(requests)
                              .retrieve()
                              .bodyToMono(PermissionTicketResponse.class))
                          .block());
  }
}
