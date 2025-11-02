package de.paladinsinn.rollenspielcons.services.geo;


import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.request.Coordinates;
import com.what3words.javawrapper.response.APIResponse.What3WordsError;
import com.what3words.javawrapper.response.ConvertTo3WA;
import com.what3words.javawrapper.response.ConvertToCoordinates;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.stereotype.Service;


/**
 * Converts between coordinates, addresses and what3words locations.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
  
  /** The what3words service */
  private final What3WordsV3 service;
  private final GeocodingService geocoding;
  
  private What3WordsV3 rateLimitedService() {
    try {
      Thread.sleep(100L);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    
    return service;
  }
  
  public GeoCoordinates convertAddressToCoordinates(final String address) {
    log.trace("enter -  {}", address);
    
    var result = geocoding.search(address).stream().findFirst().orElse(null);
    
    log.trace("exit - {}", result);
    return result;
  }
  
  /**
   * Converts the given latitude and longitude to a 3 word address.
   *
   * @param latitude  The latitude of the locations.
   * @param longitude the longitude of the locations.
   * @return the 3 word address of the locations.
   */
  public String convertTo3Words(
      @NotNull final double latitude,
      @NotNull final double longitude,
      @NotNull final String language
  ) {
    log.trace("enter -  {}, {}, {}", latitude, longitude, language);
    
    
    ConvertTo3WA coord = rateLimitedService()
        .convertTo3wa(new Coordinates(latitude, longitude))
        .language(language)
        .execute();
    
    if (!coord.isSuccessful()) {
      report3WordsError(coord.getError());
    }
    
    var result = coord.getWords();

    log.trace("exit - {}", result);
    return result;
  }
  
  private void report3WordsError(What3WordsError error) {
    switch (error) {
      case INVALID_KEY -> log.error(
          "The provided API key is invalid: error='{}', key='{}'", error.getMessage(),
          error.getKey()
      );
      case QUOTA_EXCEEDED -> log.error(
          "The request limit has been exceeded: error='{}', key='{}'", error.getMessage(),
          error.getKey()
      );
      case INTERNAL_SERVER_ERROR -> log.error(
          "There was a server error: error='{}', key='{}'", error.getMessage(), error.getKey());
      case BAD_LANGUAGE -> log.error(
          "The provided language is not supported: error='{}', key='{}'", error.getMessage(),
          error.getKey()
      );
      case BAD_INPUT -> log.error(
          "The provided format is not supported: error='{}', key='{}'", error.getMessage(),
          error.getKey()
      );
      case BAD_COORDINATES -> log.error(
          "The provided coordinates are invalid: error='{}', key='{}'", error.getMessage(),
          error.getKey()
      );
      case BAD_WORDS -> log.error(
          "The provided 3 word address is invalid: error='{}', key='{}'", error.getMessage(),
          error.getKey()
      );
      case UNKNOWN_ERROR -> log.error(
          "An unknown error occurred: error='{}', key='{}'", error.getMessage(), error.getKey());
    }
    
    throw new IllegalStateException("Could not convert 3words to coordinates: " + error.getMessage());
  }
  
  
  /**
   * Converts the given 3 word address to coordinates.
   *
   * @param threeWords The 3 word address to convert.
   * @return the coordinates of the locations in "longitude,latitude" format.
   */
  public String convertToCoordinates(final String threeWords) {
    log.trace("enter -  {}", threeWords);
    
    ConvertToCoordinates coord = rateLimitedService().convertToCoordinates(threeWords).execute();
    
    if (!coord.isSuccessful()) {
      report3WordsError(coord.getError());
    }
    
    var result = coord.getCoordinates().getLng() + "," + coord.getCoordinates().getLat();
    
    log.trace("exit - {}", result);
    return result;
  }
  
  
  /**
   * Converts the given 3 word address to a nearest known place.
   *
   * @param threeWords The 3 word address to convert.
   * @return the nearest known place to the locations.
   */
  public String convertToAddress(final String threeWords) {
    log.trace("enter -  {}", threeWords);
    
    ConvertToCoordinates coord = rateLimitedService().convertToCoordinates(threeWords).execute();
    
    if (!coord.isSuccessful()) {
      report3WordsError(coord.getError());
    }
    
    var result = coord.getNearestPlace();
    
    log.trace("exit - {}", result);
    return result;
  }
}
