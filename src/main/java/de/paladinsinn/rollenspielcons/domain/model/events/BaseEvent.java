package de.paladinsinn.rollenspielcons.domain.model.events;


import de.paladinsinn.rollenspielcons.domain.api.events.Event;
import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import de.paladinsinn.rollenspielcons.domain.api.time.TimeSpec;
import de.paladinsinn.rollenspielcons.domain.model.AbstractImportableModelBase;
import de.paladinsinn.rollenspielcons.domain.model.AbstractModelBase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * The most generic event description.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Schema(
    title = "Event event",
    description = "This is a convention event with a locations.",
    examples = {
        """
         {
           "id": 123456789012345,
           "owner": "jdoe",
           "labels": ["label1", "label2"],
           "title": {
             "name": "Event Name",
             "uri": "https://rollenspiel-cons.info"
           },
           "description": "This is a description of the event.",
        }
        """,
        """
         {
           "id": 123456789012345,
           "owner": "033432545089124104",
           "labels": ["label1", "label2"],
           "title": {
             "name": "Another Event Name",
             "uri": "https://rollenspiel-cons.info"
           },
           "description": "This is a description of the event.",
        }
        """
    }
)
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @__(@Deprecated))
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class BaseEvent extends AbstractImportableModelBase implements Event {
  @Serial
  private static final long serialVersionUID = 1L;
  
  @Schema(
      title = "The owner of this event",
      description = "The user name of the owner of this event.",
      examples = {
          "jdoe",
          "033432545089124104"
      },
      required = true
  )
  @NotBlank(message = "The owner must be present.")
  @ToString.Include
  @EqualsAndHashCode.Include
  private String owner;
  
  @Schema(
      description = "The labels for this event.",
      examples = {"[\"label1\", \"label2\"]"},
      minItems = 0,
      maxItems = 20
  )
  @Size(max = 20, message = "There must be between {min} and {max} labels.")
  @Builder.Default
  private List<String> labels = new ArrayList<>(20);
  
  
  /**
   * The markdown formatted description of the event.
   */
  @Schema(
      description = "A description of the event. The text is formatted in Markdown",
      examples = {"This is a description of the event.", "Another description."},
      maxLength = 4000
  )
  @NotNull(message = "The description must be present.")
  @Size(
      min = 1, max = 4000,
      message = "The description must be between {min} and {max} characters long."
  )
  private String description;
  
  
  @Schema(
      title = "The official website of the event",
      description = "An optional website for the event, e.g. the official event page.",
      nullable = true
  )
  @Nullable
  private WebLocation website;
  
  @Schema(
      title = "The temporal data of the event",
      description = "The temporal data including start date and optional duration of the event.",
      required = true
  )
  @NotNull(message = "The time spec must be present.")
  private TimeSpec temporalData;
  
  private String googleId;
  
  @Override
  public Optional<String> getExternalId() {
    return Optional.ofNullable(googleId);
  }
  
  
  @Override
  public String monitoredData() {
    return toString();
  }
}