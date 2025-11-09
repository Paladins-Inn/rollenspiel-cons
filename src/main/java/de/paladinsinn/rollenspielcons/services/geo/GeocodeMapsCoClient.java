package de.paladinsinn.rollenspielcons.services.geo;


import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Client to get the geocode information from maps.co.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Service
@HttpExchange
public interface GeocodeMapsCoClient {
  /**
   * Search for a location.
   * @param q The address to search for.
   * @return The geocode results.
   */
  @GetExchange(url = "/search", accept = "application/json")
  Set<GeocodeMapsResult> search(@RequestParam String q);
}
