package de.paladinsinn.rollenspielcons.services.geo;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Service
@RequiredArgsConstructor
@XSlf4j
public class GeocodingService {
  @Value("${geocoding.api.key:}")
  String apiKey;
  
  @Value("${geocoding.rate-limit:3}")
  long rateLimit; // every <rateLimit> seconds
  
  private final GeocodeMapsCoClient client;
  private final GeoCoordinateMapper mapper;
  private final LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();
  private Thread worker;
  
  @PostConstruct
  public void init() {
    log.entry(rateLimit);

    worker = new Thread(this::threadWorker, "geocode-queue-worker");
    worker.setDaemon(true);
    worker.start();
    
    log.exit(worker);
  }
  
  @PreDestroy
  public void destroy() {
    if (worker != null) {
      worker.interrupt();
      try {
        worker.join(500L);
      } catch (InterruptedException ignored) {
        Thread.currentThread().interrupt();
      }
    }
  }
  
  public Set<GeoCoordinates> search(final String address) {
    log.entry(address);
    
    Set<GeoCoordinates> result = Set.of();
    int retry = 1;
    do {
      try {
        final int retryOutput = retry;
        result = callClient(address)
            .map(r -> r.stream()
                       .map(mapper::toGeoCordinates)
                       .collect(Collectors.toSet())
            )
            .doOnError(e -> log.warn("Error while geocoding address. retry=" + retryOutput + ", address=" + address, e))
            .block();
        
      } catch (WebClientResponseException.Unauthorized e) {
        try {
          Thread.sleep(5000L * retry);
        } catch (InterruptedException interruptedException) {
          Thread.currentThread().interrupt();
        }
      }
    } while ((result == null || result.isEmpty()) && retry++ <= 3);

    return log.exit(result);
  }
  
  private Mono<Set<GeocodeMapsResult>> callClient(final String q) {
    log.entry(q);
    
    CompletableFuture<Set<GeocodeMapsResult>> future = new CompletableFuture<>();
    queue.offer(new Request(q, future));
    
    return log.exit(Mono.fromFuture(future));
  }
  
  private void threadWorker() {
    final long sleepMillis = rateLimit * 1000L;
    log.info("Geocoding queue worker started. rateLimit={}, sleepMillis={}",
             rateLimit, sleepMillis);
    
    int counter = 0;
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Request r = queue.take();
        counter++;
        log.entry(counter, r.address);
        
        try {
          Set<GeocodeMapsResult> results = client.search(r.address, apiKey);
          log.debug("Gecoding results. counter={}, address={}, results={}",
                    counter, r.address, results.size());
          r.future.complete(results);
        } catch (Exception e) {
          log.warn("Error while processing geocoding request. address="
                   + r.address + ", counter=" + counter, e);
          log.throwing(e);
          r.future.completeExceptionally(e);
        }
        
        // Enforce rate limit
        try {
          Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      } catch (Exception e) {
        log.error("Error while processing geocoding request.", e);
      }
    }
    
    log.info("Geocoding queue worker stopped. counter={}", counter);
  }
  
  @AllArgsConstructor
  @Getter
  private static final class Request {
    final String address;
    final CompletableFuture<Set<GeocodeMapsResult>> future;
  }
}
