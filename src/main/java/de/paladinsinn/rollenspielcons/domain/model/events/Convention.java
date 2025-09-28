package de.paladinsinn.rollenspielcons.domain.model.events;


import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 * A convention event with a location.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Schema(
    title = "Convention event",
    description = "This is a convention event with a location.",
    examples = {
        """
         {
           "id": 123456789012345,
           "labels": ["label1", "label2"],
           "startDay": "2024-12-24",
           "startTime": "18:00:00",
           "duration": "PT6H",
           "name": "Event Name",
           "description": "This is a description of the event.",
           "location": "Darmstädter Str. 12, 64625 Bensheim"
        }
        """,
        """
         {
           "id": 123456789012345,
           "labels": ["label1", "label2"],
           "startDay": "2024-12-24",
           "name": "Event Name",
           "description": "This is a description of the event.",
           "location": "Darmstädter Str. 12, 64625 Bensheim"
        }
        """
    }
)
@Jacksonized
@SuperBuilder(toBuilder = true, setterPrefix = "")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Convention extends BaseEvent {
  @Schema(
      title = "Location of the convention",
      description = "The location where the convention takes place.",
      examples = {
          "Darmstädter Str. 12, 64625 Bensheim",
          "Messegelände 1, 60327 Frankfurt am Main"
      }
  )
  @Size(min = 3, max = 250, message = "Location must be between 3 and 250 characters long")
  private String location;
}
