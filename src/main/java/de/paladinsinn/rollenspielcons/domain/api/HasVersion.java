package de.paladinsinn.rollenspielcons.domain.api;


import jakarta.validation.constraints.Min;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
public interface HasVersion {
  @Schema(
      description = "The version of the dataset.",
      comment = "Thhis is the version identifier of the dataset.",
      examples = { "123456789012345", "987654321098765432" },
      minimum = "0",
      required = true
  )
  @Min(value = 0, message = "The version must be greater than {value}. Please don't change this value")
  int getVersion();
}
