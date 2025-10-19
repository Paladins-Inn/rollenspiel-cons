package de.paladinsinn.rollenspielcons.domain.model.importers;


import de.paladinsinn.rollenspielcons.domain.api.integrations.ImporterAuthentication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpHeaders;


/**
 * An empty authentication object.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
@ToString
@XSlf4j
public class NullAuthentication implements ImporterAuthentication {
  @Override
  public HttpHeaders authenticate(final HttpHeaders httpHeaders) {
    log.entry(httpHeaders);
    return log.exit(httpHeaders);
  }
}
