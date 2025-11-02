package de.paladinsinn.rollenspielcons.services.security;

import de.paladinsinn.rollenspielcons.services.security.model.PermissionRequest;
import de.paladinsinn.rollenspielcons.services.security.model.PermissionTicketResponse;
import de.paladinsinn.rollenspielcons.services.security.model.ResourceRepresentation;
import de.paladinsinn.rollenspielcons.services.security.model.ResourceResponse;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakProtectionClient {
  private final WebClient.Builder webClientBuilder;
  private final KeycloakTokenClient tokens;
  
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String protectionApiUrl;
  
  private WebClient protectionClient;
  
  
  @PostConstruct
  public void init() {
    log.trace("enter -  {}, {}", webClientBuilder, protectionApiUrl);
    
    protectionClient = webClientBuilder.baseUrl(protectionApiUrl)
                                       .defaultHeader("Accept", "application/json")
                                       .build();
    
    log.trace("exit - {}", new Object[] {protectionClient});
  }
  
  
  private WebClient withBearer(WebClient wc, String token) {
    log.trace("enter -  {}, {}", wc, token);
    
    var result = wc.mutate()
        .defaultHeaders(h -> h.setBearerAuth(token))
        .build();

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Resource registrieren -> liefert ResourceResponse mit id
   */
  @SuppressWarnings("unused")
  public ResourceResponse createResource(ResourceRepresentation res) {
    log.trace("enter -  {}", res);
    
    var result = tokens
        .getProtectionApiToken()
        .flatMap(pat -> withBearer(protectionClient, pat)
        .post()
        .uri("/resource_set")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(res)
        .retrieve()
        .bodyToMono(ResourceResponse.class))
        .block();

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Resource auslesen
   */
  @SuppressWarnings("unused")
  public ResourceResponse getResource(String resourceId) {
    log.trace("enter -  {}", resourceId);
    
    var result = tokens
        .getProtectionApiToken()
        .flatMap(pat -> withBearer(protectionClient, pat)
        .get()
        .uri("/resource_set/{id}", resourceId)
        .retrieve()
        .bodyToMono(ResourceResponse.class))
        .block();

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Resource löschen
   */
  @SuppressWarnings("unused")
  public void deleteResource(String resourceId) {
    log.trace("enter -  {}", resourceId);
    
    tokens.getProtectionApiToken()
          .flatMap(pat -> withBearer(protectionClient, pat)
              .delete()
              .uri("/resource_set/{id}", resourceId)
              .retrieve()
              .bodyToMono(Void.class)).block()
    ;
    
    log.trace("Exiting method");
  }
  
  /**
   * Ressourcen des aufrufenden RS-Clients auflisten (optional paginiert)
   */
  @SuppressWarnings("unused")
  public List<ResourceResponse> listResources() {
    log.trace("enter - ");
    
    var result = tokens
        .getProtectionApiToken()
        .flatMapMany(pat -> withBearer(protectionClient, pat)
        .get()
        .uri(uriBuilder -> uriBuilder.path("/resource_set").build())
        .retrieve()
        .bodyToFlux(ResourceResponse.class)).toStream().toList();

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Permission Ticket für eine oder mehrere Anfragen erzeugen
   */
  @SuppressWarnings("unused")
  public PermissionTicketResponse createPermissionTicket(List<PermissionRequest> requests) {
    log.trace("enter -  {}", requests);
    
    var result = tokens.getProtectionApiToken()
        .flatMap(pat -> withBearer(protectionClient, pat)
        .post()
        .uri("/permission")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requests)
        .retrieve()
        .bodyToMono(PermissionTicketResponse.class))
        .block();

    log.trace("exit - {}", result);
    return result;
  }
}
