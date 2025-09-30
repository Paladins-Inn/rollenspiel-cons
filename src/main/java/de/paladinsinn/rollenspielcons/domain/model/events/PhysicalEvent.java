package de.paladinsinn.rollenspielcons.domain.model.events;


import de.paladinsinn.rollenspielcons.domain.model.locations.PhysicalAddress;
import de.paladinsinn.rollenspielcons.domain.model.locations.WebLocation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 * A convention event with a locations.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Schema(
    title = "PhysicalEvent event",
    description = "This is a convention event with a locations.",
    examples = {
        """
        {
          "id": 123456789012345,
          "labels": ["label1", "label2"],
          "temporalData": {
            "startDay": "2024-12-24T18:00:00+01:00[Europe/Berlin]",
            "duration": "PT6H"
          },
          "title": {
            "name": "PhysicalEvent Name",
          },
          "description": "This is a description of the event.",
          "locations": [
            {
              "name": "Location 1",
              "address": "123 Main St, Anytown, Country",
              "what3words": "filled.count.soap",
              "latitude": 51.5074,
              "longitude": -0.1278
            },
            {
              "name": "Location 2",
              "address": "456 Side St, Othertown, Country",
              "what3words": "index.home.raft",
              "latitude": 48.8566,
              "longitude": 2.3522
            }
          ]
        }
        """,
        """
        {
          "id": 123456789012345,
          "labels": ["label1", "label2"],
           "title": {
             "name": "Another AbstractBaseEvent",
           },
          "description": "This is a description of the event.",
          "temporalData": {
            "startDay": "2024-12-24T00:00:00+01:00[Europe/Berlin]"
          },
          "locations": [
            {
              "name": "Location 1",
              "address": "123 Main St, Anytown, Country",
              "what3words": "filled.count.soap",
              "latitude": 51.5074,
              "longitude": -0.1278
            }
          ],
          "website": {
            "displayText": "Official Event Website",
            "uri": "https://paladins-inn.de/paladins-con/"
          }
        }
        """
    }
)
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PhysicalEvent extends AbstractTimedEvent {
  @Schema(
      title = "The physical locations of the event",
      description = "An array of physical locations where the event takes place. At least one locations is required.",
      required = true,
      minItems = 1,
      maxItems = 10
  )
  @NotNull
  @Size(min = 1, max = 10, message = "A single convention can have between 1 and 10 locations")
  @Builder.Default
  private Set<PhysicalAddress> locations = new HashSet<>(10);

  @Schema(
      title = "The official website of the event",
      description = "An optional website for the event, e.g. the official event page.",
      nullable = true
  )
  @Nullable
  private WebLocation website;
}
