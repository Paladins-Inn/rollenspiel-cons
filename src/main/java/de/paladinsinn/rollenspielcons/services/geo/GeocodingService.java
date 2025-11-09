package de.paladinsinn.rollenspielcons.services.geo;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-18
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GeocodingService {
  @Value("${geocoding.rate-limit:3}")
  long rateLimit; // every <rateLimit> seconds
  
  private final GeocodeMapsCoClient client;
  private final GeoCoordinateMapper mapper;
  private final LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();
  private Thread worker;
  
  @PostConstruct
  public void init() {
    log.trace("enter -  {}", rateLimit);

    worker = new Thread(this::threadWorker, "geocode-queue-worker");
    worker.setDaemon(true);
    worker.start();
    
    log.trace("exit - {}", worker);
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
    log.trace("enter -  {}", address);
    
    Set<GeoCoordinates> result = Set.of();
    int attempt = 1;
    do {
      try {
        result = callClient(address)
            .stream()
              .map(mapper::toGeoCordinates)
              .collect(Collectors.toSet());
      } catch (RestClientResponseException e) {
        log.debug("Rest client error while geocoding address. retry={}, address={}, status={}, response={}",
                  attempt, address, e.getStatusCode().value(), e.getResponseBodyAsString(), e);
        
        sleep(attempt);
      } catch (ExecutionException|InterruptedException e) {
        log.debug("Error while geocoding address. retry={}, address={}",
                  attempt, address, e);
        
        sleep(attempt);
      }
    } while (result.isEmpty() && attempt++ <= 3);
    
    log.trace("exit - {}", result);
    return result;
  }
  
  private static void sleep(final int attempt) {
    try {
      Thread.sleep(5000L * attempt);
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
    }
  }
  
  private Set<GeocodeMapsResult> callClient(final String q)
      throws ExecutionException, InterruptedException {
    log.trace("enter -  {}", q);
    
    CompletableFuture<Set<GeocodeMapsResult>> future = new CompletableFuture<>();
    queue.offer(new Request(q, future));
    
    var result = future.get();

    log.trace("exit - {}", result);
    return result;
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
        log.trace("enter -  {}, {}", counter, r.address);
        
        try {
          Set<GeocodeMapsResult> results = client.search(r.address);
          log.debug("Gecoding results. counter={}, address={}, results={}",
                    counter, r.address, results.size());
          r.future.complete(results);
        } catch (Exception e) {
          log.warn("Error while processing geocoding request. address="
                   + r.address + ", counter=" + counter, e);
          r.future.completeExceptionally(e);
        }
        
        // Enforce rate limit
        try {
          //noinspection BusyWait
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
