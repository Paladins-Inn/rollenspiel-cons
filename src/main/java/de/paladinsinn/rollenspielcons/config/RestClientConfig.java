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

package de.paladinsinn.rollenspielcons.config;

import de.paladinsinn.rollenspielcons.services.geo.GeocodeMapsCoClient;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;


/**
 * Creates a REST client for the Geocode Maps Co service.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class RestClientConfig {
  
  @Bean
  public RestClient restClient(@NotNull @org.springframework.context.annotation.Lazy final RestClient.Builder builder) {
    log.trace("enter - ");
    
    var result = builder.build();
    
    log.trace("exit - {}", result);
    return result;
  }

  @Bean
  public GeocodeMapsCoClient geocodeMapsCoClient(
      @NotNull @Value("${geocoding.api.url") final String url,
      @NotNull @org.springframework.context.annotation.Lazy RestClient.Builder clientBuilder,
      @NotNull JdkClientHttpRequestFactory requestFactory,
      @NotNull UriBuilderFactory apiKeyInjectorFactory,
      @NotNull OAuth2ClientHttpRequestInterceptor oauth2Interceptor
  ) {
    log.trace("enter - {}, {}, {}, {}", url, clientBuilder, requestFactory, oauth2Interceptor);
    
    var restClient = restClient(
        url,
        clientBuilder,
        requestFactory,
        apiKeyInjectorFactory,
        oauth2Interceptor
    );

    GeocodeMapsCoClient result = HttpServiceProxyFactory.builder()
        .exchangeAdapter(RestClientAdapter.create(restClient))
        .build()
        .createClient(GeocodeMapsCoClient.class);
    
    log.trace("exit - {}", result.getClass());
    
    return result;
  }
  
  private RestClient restClient(
      @NotNull final String url,
      @NotNull RestClient.Builder clientBuilder,
      @NotNull JdkClientHttpRequestFactory requestFactory,
      @NotNull UriBuilderFactory apiKeyInjectorFactory,
      @NotNull OAuth2ClientHttpRequestInterceptor oauth2Interceptor
  ) {
    log.trace("enter - {}, {}, {}, {}, {}", url, clientBuilder, requestFactory, apiKeyInjectorFactory, oauth2Interceptor);
    
    var result = clientBuilder
        .requestFactory(requestFactory)
        .uriBuilderFactory(apiKeyInjectorFactory)
        .baseUrl(url)
        .requestInterceptor(oauth2Interceptor)
        .build();
    
    log.trace("exit - {}", result);
    return result;
  }
  
  @Bean
  public JdkClientHttpRequestFactory jdkClientHttpRequestFactory() {
    log.trace("enter - ");
    
    JdkClientHttpRequestFactory result = new JdkClientHttpRequestFactory();
    
    log.trace("exit - {}", result);
    return result;
  }
  
  @Bean
  public UriBuilderFactory uriBuilderFactory(
      @Value("${geocoding.api.key:changeme}") final String apiKey
  ) {
    log.trace("enter - {}", apiKey);
    
    var result = new DefaultUriBuilderFactory();
    result.setDefaultUriVariables(Map.of("api_key", apiKey));
    
    log.trace("exit - {}", result);
    return result;
  }
  
  
  @Bean
  public OAuth2ClientHttpRequestInterceptor oauth2HttpRequestInterceptor(
      @NotNull OAuth2AuthorizedClientManager oauth2Manager
  ) {
    log.trace("enter - {}", oauth2Manager);
    
    OAuth2ClientHttpRequestInterceptor result = new OAuth2ClientHttpRequestInterceptor(oauth2Manager);
    
    log.trace("exit - {}", result);
    return result;
  }
  
  @Bean
  @Lazy
  @ConditionalOnMissingBean(RestClient.Builder.class)
  public RestClient.Builder restClientBuilder() {
    return RestClient.builder();
  }
}
