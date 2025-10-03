package de.paladinsinn.rollenspielcons.domain.api;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-03
 */
public interface HasOwner {
  /**
   * The owner of this object.
   *
   * <p>This is normally the sub of the identity.</p>
   *
   * @return the owner of this object
   */
  @Schema(
      description = "The owner of this object.",
      comment = "This is normally the sub of the identity.",
      examples = {
          "123451234512345"
      },
      minLength = 2,
      maxLength = 100,
      required = true
  )
  @NotBlank(message = "The owner has to be set.")
  @Size(min = 2, max = 100, message = "The owner must be between {min} and {max} characters long.")
  String getOwner();
}
