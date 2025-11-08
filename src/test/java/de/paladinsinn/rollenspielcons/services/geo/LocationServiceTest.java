package de.paladinsinn.rollenspielcons.services.geo;


import de.paladinsinn.rollenspielcons.config.LocationConfig;
import de.paladinsinn.rollenspielcons.config.RestClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import com.what3words.javawrapper.What3WordsJavaWrapper;
import com.what3words.javawrapper.What3WordsV3;
import okhttp3.OkHttpClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.net.ssl.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-02
 */
@Disabled("Only for manual smoke check")
@SpringBootTest(
    classes = {
        LocationConfig.class,
        LocationService.class,
        GeocodingService.class,
        GeocodeMapsResult.class,
        GeocodeMapsCoClient.class,
        RestClientConfig.class,
        GeoCoordinateMapperImpl.class
    }
)
@Slf4j
@ActiveProfiles({"test","local"})
public class LocationServiceTest {

  @Autowired
  private LocationService sut;
  
  @Test
  public void shouldLocate3WordAdress() {
    log.trace("enter - {}", sut);
    
    String what3WordsAddress = "filled.count.soap";
    String result            = sut.convertToCoordinates(what3WordsAddress);
    log.info("Result: {}", result);
    
    log.trace("exit - ");
  }
  
  
  @TestConfiguration
  public class W3WTruststoreTestConfig {
  
    @Bean
    @Primary
    public What3WordsJavaWrapper what3wordsClient() throws Exception {
      String truststorePath = "target/generated-resources/tls/test-truststore.jks";
      String truststorePassword = "changeit";
      String apiKey = "dummy";
      String baseUrl = "https://localhost:8089/v3/";
  
      KeyStore ts = KeyStore.getInstance("JKS");
      try (var in = Files.newInputStream(Path.of(truststorePath))) {
        ts.load(in, truststorePassword.toCharArray());
      }
  
      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ts);
      X509TrustManager tm = Arrays.stream(tmf.getTrustManagers())
          .filter(X509TrustManager.class::isInstance)
          .map(X509TrustManager.class::cast)
          .findFirst()
          .orElseThrow();
  
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, new TrustManager[]{tm}, new SecureRandom());
  
      OkHttpClient ok = new OkHttpClient.Builder()
          .sslSocketFactory(sc.getSocketFactory(), tm)
          .build();
  
      return new What3WordsV3(apiKey, baseUrl);
    }
  }
}