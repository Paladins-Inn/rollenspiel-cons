package de.paladinsinn.rollenspielcons.domain.model.events;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.model.DisplayableName;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import de.paladinsinn.rollenspielcons.domain.model.time.TimeSpec;
import de.paladinsinn.rollenspielcons.domain.model.time.Timed;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.beans.Transient;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 * Base class for all events.
 *
 * @author klenkes74
 * @since 21.09.25
 */
@Schema(
    title = "AbstractTimedEvent for all events",
    description = "This is the base class consisting of the minimal set of attributes for an event.",
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
             "name": "AbstractTimedEvent Name",
             "uri": "https://rollenspiel-cons.info"
           },
           "description": "This is a description of the event."
         }
         """,
        """
         {
           "id": 123456789012345,
           "labels": ["label1", "label2"],
           "temporalData": {
             "startDay": "2024-12-24"
           },
           "title": {
             "name": "Another AbstractTimedEvent",
             "uri": "https://rollenspiel-cons.info"
           },
           "description": "This is another description of the event."
         }
         """
    }
)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString()
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractTimedEvent implements Event, HasId, HasDisplayText, Timed {
  @Min(value = 1, message = "The id must be greater than 0.")
  private long id;

  @Builder.Default
  private List<String> labels = new LinkedList<>();
  
  @NotNull
  private TimeSpec temporalData;

  @NotNull
  private DisplayableName title;

  @NotNull
  private String description;
  
  @JsonIgnore
  @Transient
  @Override
  public String getUri() {
    return title.getUri();
  }
  
  @Override
  public String monitoredData() {
    return id
           + labels.stream().reduce("", (a, b) -> a + b)
           + temporalData
           + title
        ;
  }
}
