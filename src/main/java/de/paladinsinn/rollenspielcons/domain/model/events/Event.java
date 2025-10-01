package de.paladinsinn.rollenspielcons.domain.model.events;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasEtag;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.beans.Transient;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * The most generic event description.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Schema(
    title = "PhysicalEvent event",
    description = "This is a convention event with a locations.",
    examples = {
        """
         {
           "id": 123456789012345,
           "labels": ["label1", "label2"],
           "title": {
             "name": "PhysicalEvent Name",
             "uri": "https://rollenspiel-cons.info"
           },
           "description": "This is a description of the event.",
        }
        """,
        """
         {
           "id": 123456789012345,
           "labels": ["label1", "label2"],
           "title": {
             "name": "Another PhysicalEvent Name",
             "uri": "https://rollenspiel-cons.info"
           },
           "description": "This is a description of the event.",
        }
        """
    }
)
public interface Event extends HasId, HasDisplayText, HasEtag {
  @Schema(
      description = "The labels for this event.",
      examples = { "[\"label1\", \"label2\"]" },
      minItems = 0,
      maxItems = 20
  )
  @Size(max = 20, message = "There must be between {min} and {max} labels.")
  List<String> getLabels();

  @Schema(
      description = "The title of the event.",
      required = true
  )
  @NotNull
  HasDisplayText getTitle();
  
  @JsonIgnore
  @Transient
  default String getDisplayText() {
    return getTitle().getDisplayText();
  }
  
  
  /**
   * The markdown formatted description of the event.
   * @return the description
   */
  @Schema(
      description = "A description of the event. The text is formatted in Markdown",
      examples = { "This is a description of the event.", "Another description." },
      maxLength = 4000
  )
  @NotNull(message = "The description must be present.")
  @Size(min = 1, max = 4000, message = "The description must be between {min} and {max} characters long.")
  String getDescription();
}
