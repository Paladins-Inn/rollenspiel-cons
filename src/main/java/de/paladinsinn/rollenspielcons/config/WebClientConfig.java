package de.paladinsinn.rollenspielcons.config;


import de.paladinsinn.rollenspielcons.services.geo.GeocodeMapsCoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientConfig {
  @Bean
  public WebClient webClient() {
    log.trace("enter - ");
    
    var result = WebClient.builder().build();

    log.trace("exit - {}", result);
    return result;
  }
  
  @Bean
  public GeocodeMapsCoClient geocodeMapsCoClient() {
    WebClient webClient = WebClient.builder()
        .baseUrl("https://geocode.maps.co")
        .build();

    GeocodeMapsCoClient result = HttpServiceProxyFactory.builder()
        .exchangeAdapter(WebClientAdapter.create(webClient))
        .build()
        .createClient(GeocodeMapsCoClient.class);
    
    log.trace("exit - {}", result.getClass());
    return result;
  }
}
