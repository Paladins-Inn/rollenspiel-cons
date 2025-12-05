/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakProtectionClient {
  private final KeycloakTokenClient tokens;
  private final RestClient.Builder restClientBuilder;
  
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String protectionApiUrl;
  
  private RestClient protectionClient;
  
  
  @PostConstruct
  public void init() {
    log.trace("enter -  {}, {}", restClientBuilder, protectionApiUrl);
    
    protectionClient = restClientBuilder
        .baseUrl(protectionApiUrl)
        .defaultHeader("Accept", "application/json")
        .build();

    log.trace("exit - {}", new Object[] {protectionClient});
  }
  
  
  private RestClient withBearer(RestClient wc, String token) {
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
    
    var result = withBearer(protectionClient, tokens.getProtectionApiToken())
        .post()
        .uri("/resource_set")
        .contentType(MediaType.APPLICATION_JSON)
        .body(res)
        .retrieve()
        .body(ResourceResponse.class);

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Resource auslesen
   */
  @SuppressWarnings("unused")
  public ResourceResponse getResource(String resourceId) {
    log.trace("enter -  {}", resourceId);
    
    var result = withBearer(protectionClient, tokens.getProtectionApiToken())
        .get()
        .uri("/resource_set/{id}", resourceId)
        .retrieve()
        .body(ResourceResponse.class);

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Resource löschen
   */
  @SuppressWarnings("unused")
  public void deleteResource(String resourceId) {
    log.trace("enter -  {}", resourceId);
    
    var result = withBearer(protectionClient, tokens.getProtectionApiToken())
              .delete()
              .uri("/resource_set/{id}", resourceId)
              .retrieve()
              .body(Void.class);
    
    log.trace("Exiting method");
  }
  
  /**
   * Ressourcen des aufrufenden RS-Clients auflisten (optional paginiert)
   */
  @SuppressWarnings("unused")
  public List<ResourceResponse> listResources() {
    log.trace("enter - ");
    
    var result = withBearer(protectionClient, tokens.getProtectionApiToken())
        .get()
        .uri(uriBuilder -> uriBuilder.path("/resource_set").build())
        .retrieve()
        .body(new ParameterizedTypeReference<List<ResourceResponse>>() {});

    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Permission Ticket für eine oder mehrere Anfragen erzeugen
   */
  @SuppressWarnings("unused")
  public PermissionTicketResponse createPermissionTicket(List<PermissionRequest> requests) {
    log.trace("enter -  {}", requests);
    
    var result = withBearer(protectionClient, tokens.getProtectionApiToken())
        .post()
        .uri("/permission")
        .contentType(MediaType.APPLICATION_JSON)
        .body(requests)
        .retrieve()
        .body(PermissionTicketResponse.class);

    log.trace("exit - {}", result);
    return result;
  }
}
