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


import de.paladinsinn.rollenspielcons.services.security.model.TokenResponse;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class KeycloakTokenClient {
  private final RestClient.Builder webClientBuilder;
  
  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String protectionApiUrl;
  
  @Value("${spring.security.oauth2.client.registration.sso.client-id}")
  private String clientId;
  
  @Value("${spring.security.oauth2.client.registration.sso.client-secret}")
  private String clientSecret;
  
  
  private RestClient webClient;
  
  @PostConstruct
  public void init() {
    log.trace("enter -  {}, {}", webClientBuilder, protectionApiUrl);
    
    webClient = webClientBuilder.baseUrl(protectionApiUrl + "/protocol/openid-connect/token")
                                .defaultHeader("Accept", "application/json")
                                .build();
    
    log.trace("exit - {}", new Object[] {webClient});
  }
  
  /**
   * Holt ein PAT (Protection API Token) über Client Credentials.
   */
  public String getProtectionApiToken() {
    log.trace("enter - ");
    
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "client_credentials");
    // scope ist nicht immer nötig, schadet aber nicht:
    form.add("scope", "uma_protection");
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    
    var result = webClient
        .post()
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(form)
        .retrieve()
        .body(TokenResponse.class)
        .getAccessToken();
    
    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * (Optional) Tauscht ein Permission-Ticket gegen ein RPT. Normalerweise macht das der Client
   * (z. B. SPA).
   */
  @SuppressWarnings("unused")
  public String exchangeUmaTicketForRpt(String ticket, String audience) {
    log.trace("enter - ");
    
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "urn:ietf:params:oauth:grant-type:uma-ticket");
    form.add("ticket", ticket);
    form.add("audience", audience); // = dein RS-Client (z. B. my-api)
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    
    var result = webClient
        .post()
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(form)
        .retrieve()
        .body(TokenResponse.class)
        .getAccessToken();

    log.trace("exit - {}", result);
    return result;
  }
}
