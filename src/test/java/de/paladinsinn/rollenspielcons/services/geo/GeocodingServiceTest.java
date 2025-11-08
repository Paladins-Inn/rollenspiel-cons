package de.paladinsinn.rollenspielcons.services.geo;


import de.paladinsinn.rollenspielcons.config.RestClientConfig;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
//TODO 2025-10-19 klenkes74: Add tests with wiremock for this
@Disabled("Only for manual smoke check")
@SpringBootTest(
    classes = {
        GeocodingService.class,
        GeocodeMapsResult.class,
        GeocodeMapsCoClient.class,
        RestClientConfig.class,
        GeoCoordinateMapperImpl.class
    }
)
@Slf4j
public class GeocodingServiceTest {
  private static final String TEST_ADDRESS = "Darmstädter Str. 12, 64625 Bensheim";
  private static final String[] TEST_ADDRESSES = {
      TEST_ADDRESS,
      "Ernst-Blickle-Straße 42, 76464 Bruchsal",
      "Hildastraße 32, 76297 Stutensee",
      "Rheinstraße 84, 76297 Stutensee",
      "Walonenstraße 12, 76297 Stutensee"
  };
  
  @Autowired
  private GeocodingService sut;
  
  @Test
  public void shouldFindCoordinatesForAddress() {
    log.trace("enter - ");
    
    Set<GeoCoordinates> result = sut.search(TEST_ADDRESS);
    log.info("Result: {}", result);
    
    log.trace("exit - ");
  }
  
  @Test
  public void shouldThrottleToOnePerSecondWhenMultipleRequestsAreSentInParallel() throws Exception {
    log.trace("enter - ");
    int             parallel = 5;
    ExecutorService exec     = Executors.newFixedThreadPool(parallel);
    CountDownLatch  start    = new CountDownLatch(1);
    
    List<CompletableFuture<Set<GeoCoordinates>>> futures = IntStream
        .range(0, parallel)
        .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
          try {
            start.await();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
          }
          return sut.search(TEST_ADDRESSES[i]);
        }, exec))
        .toList()
        ;

    long t0 = System.nanoTime();
    start.countDown(); // alle Worker starten gleichzeitig
    List<Set<GeoCoordinates>> results = futures
        .stream()
        .map(CompletableFuture::join)
        .toList()
        ;
    long durationSec = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - t0);
    
    log.info("Sent {} parallel requests, took {}s, result sizes: {}", parallel, durationSec,
             results.stream().map(Set::size).collect(Collectors.toList()));
    
    exec.shutdownNow();
    
    // einfache Validierung: alle Futures lieferten ein Ergebnis
    if (results.size() != parallel) {
      throw new AssertionError("Nicht alle Anfragen haben ein Ergebnis geliefert");
    }
    
    log.trace("exit - ");
  }
}
