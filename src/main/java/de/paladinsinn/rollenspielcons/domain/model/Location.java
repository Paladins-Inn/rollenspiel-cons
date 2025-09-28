package de.paladinsinn.rollenspielcons.domain.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;


/**
 * A full location for all used systems.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Schema(
    title = "Location",
    description = "A location with address and coordinates.",
    examples = {
        """
         {
           "address": "Darmstädter Str. 12, 64625 Bensheim",
           "threeWords": "neuerung.sportler.zelter",
           "longitute": 8.621361,
           "latitude": 49.684139
         }
         """,
        """
         {
           "address": "Messegelände 1, 60327 Frankfurt am Main",
           "threeWords": "dörfern.beweis.zwischen",
           "longitute": 8.2769618,
           "latitude": 49.8887193
         }
         """
    }
)
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
@EqualsAndHashCode(of = {"longitute", "latitude"})
public class Location {
  @Schema(
      title = "Address of the location",
      description = "The full address of the location.",
      examples = {
          "Darmstädter Str. 12, 64625 Bensheim",
          "Messegelände 1, 60327 Frankfurt am Main"
      },
      required = true
  )
  @NotNull
  @Size(min = 5, max = 250, message = "Address must be between 5 and 250 characters long")
  private String address;
  
  @Schema(
      title = "What3Words location",
      description = "The What3Words location of the address.",
      examples = {
          "neuerung.sportler.zelter",
          "dörfern.beweis.zwischen"
      },
      required = true,
      pattern = "^[a-zA-ZäöüÄÖÜß]+\\.[a-zA-ZäöüÄÖÜß]+\\.[a-zA-ZäöüÄÖÜß]+$"
  )
  @NotNull
  @Pattern(regexp = "^[a-zA-ZäöüÄÖÜß]+\\.[a-zA-ZäöüÄÖÜß]+\\.[a-zA-ZäöüÄÖÜß]+$", message = "threeWords must be in the format word1.word2.word3 with only letters and German umlauts")
  private String threeWords;

  @Schema(
      title = "Longitude of the location",
      description = "The longitude of the location in decimal degrees.",
      examples = { "8.621361", "8.2769618" },
      minimum = "-180",
      maximum = "180",
      required = true
  )
  @NotNull
  @Range(min = -180, max = 180, message = "Longitude must be between {min} and {max}")
  private Double longitute;
  
  @Schema(
      title = "Latitude of the location",
      description = "The latitude of the location in decimal degrees.",
      examples = { "49.684139", "49.8887193" },
      minimum = "-90",
      maximum = "90",
      required = true
  )
  @NotNull
  @Range(min = -90, max = 90, message = "Latitude must be between {min} and {max}")
  private Double latitude;
}
