package de.paladinsinn.rollenspielcons.domain.model.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.PhysicalAddress;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
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
    title = "PhysicalAddress",
    description = "A locations with address and coordinates.",
    examples = {
        """
         {
           "address": "Darmstädter Str. 12, 64625 Bensheim",
           "threeWords": "neuerung.sportler.zelter",
           "longitude": 8.621361,
           "latitude": 49.684139
         }
         """,
        """
         {
           "address": "Messegelände 1, 60327 Frankfurt am Main",
           "threeWords": "dörfern.beweis.zwischen",
           "longitude": 8.2769618,
           "latitude": 49.8887193
         }
         """
    }
)
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = {"longitude", "latitude"}, callSuper = true)
public class PhysicalAddressImpl extends LocationImpl implements PhysicalAddress {
  
  @Schema(
      title = "Address of the locations",
      description = "The full address of the locations.",
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
      title = "What3Words locations",
      description = "The What3Words locations of the address.",
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
      title = "Longitude of the locations",
      description = "The longitude of the locations in decimal degrees.",
      examples = { "8.621361", "8.2769618" },
      minimum = "-180",
      maximum = "180",
      required = true
  )
  @NotNull
  @Range(min = -180, max = 180, message = "Longitude must be between {min} and {max}")
  private Double longitude;
  
  @Schema(
      title = "Latitude of the locations",
      description = "The latitude of the locations in decimal degrees.",
      examples = { "49.684139", "49.8887193" },
      minimum = "-90",
      maximum = "90",
      required = true
  )
  @NotNull
  @Range(min = -90, max = 90, message = "Latitude must be between {min} and {max}")
  private Double latitude;
  
  
  @Override
  public String getDisplayText() {
    return address;
  }
  
  @Override
  public String getEmailText() {
    return address + " (" + getUri() + ")";
  }
  
  @Override
  public String getUri() {
    return "https://www.openstreetmap.org/#map=19/" + longitude + "/" + latitude;
  }
}
