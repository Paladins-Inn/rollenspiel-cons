package de.paladinsinn.rollenspielcons.domain.model.events;


import de.paladinsinn.rollenspielcons.domain.api.events.WebEvent;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
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
          "owner": "jdoe",
          "labels": ["label1", "label2"],
          "title": {
            "name": "WebEvent Name",
          },
          "description": "This is a description of the event.",
          "temporalData": {
            "startDay": "2024-12-24T18:00:00+01:00[Europe/Berlin]",
            "duration": "PT6H"
          },
          "locations": [
            {
              "displayText": "Location 1",
              "uri": "https://www.location1.com"
            },
            {
              "displayText": "Location 2",
              "uri": "https://www.location2.com"
            }
          ],
          "website": {
            "displayText": "Official Event Website",
            "uri": "https://www.event-website.com"
          }
        }
        """,
        """
        {
          "id": 123456789012345,
          "owner": "033432545089124104",
          "labels": ["label1", "label2"],
          "title": {
            "name": "Another WebEvent"
          },
          "description": "This is a description of the event.",
          "temporalData": {
            "startDay": "2024-12-24T00:00:00+01:00[Europe/Berlin]",
          },
          "locations": [
            {
              "displayText": "Location 1",
              "uri": "https://www.location1.com"
            }
          ]
        }
        """
    }
)
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @__(@Deprecated))
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WebEventImpl extends AbstractEvent implements WebEvent {
  @Schema(
      title = "Weblocations of this event",
      description = "The locations for this event.",
      examples = {
          """
          [
            {
              "displayText": "Location 1",
              "uri": "https://www.location1.com"
            },
            {
              "displayText": "Location 2",
              "uri": "https://www.location2.com"
            }
          ]
          """,
          """
          [
            {
              "displayText": "Location 1",
              "uri": "https://www.location1.com"
            }
          ]
          """
      },
      minItems = 1,
      maxItems = 10,
      required = true
  )
  @NotNull
  @Size(min = 1, max = 10, message = "A single convention can have between 1 and 10 locations")
  @Builder.Default
  private Set<WebLocation> locations = new HashSet<>(10);
}
