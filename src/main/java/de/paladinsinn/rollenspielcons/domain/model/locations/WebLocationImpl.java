package de.paladinsinn.rollenspielcons.domain.model.locations;


import de.paladinsinn.rollenspielcons.domain.api.locations.WebLocation;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Schema(
    title = "WebLocation",
    description = "A web locations with a display name and a URI.",
    examples = {
        """
        {
          "displayText": "Paladins Inn",
          "uri": "https://www.paladins-inn.de"
        }
        """,
        """
        {
          "displayText": "Discord Server",
          "uri": "https://discord.gg/your-invite-code"
        }
        """
    })
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @__(@Deprecated))
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class WebLocationImpl extends LocationImpl implements WebLocation {
  @Schema(
      title = "Name of the website",
      description = "The display name of the website.",
      example = "Paladins Inn"
  )
  @Size(min = 1, max = 250, message = "The name must be between {min} and {max} characters long.")
  private String displayText;
  
  @Schema(
      title = "The URI of the website",
      description = "The URI (link) to the website.",
      example = "https://discord.gg/your-invite-code"
  )
  @Size(min = 10, max = 250, message = "The URI must be between {min} and {max} characters long.")
  private String uri;
}
