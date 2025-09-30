package de.paladinsinn.rollenspielcons.domain.model.iam;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.model.DisplayableName;
import de.paladinsinn.rollenspielcons.domain.model.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.model.HasId;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.beans.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode(of = {"id"})
public class OidcUser implements HasId, HasDisplayText, HasIdentity {
  /**
   * The unique identifier of this OIDC user.
   */
  @Min(value = 0, message = "The id must be greater than or equal to {value}.")
  private long id;
  
  /**
   * The identity that is linked to this OIDC user.
   */
  @NotNull(message = "The identity must not be null.")
  private Identity identity;
  
  /**
   * The issuer of this OIDC user.
   */
  @NotNull(message = "The issuer must not be null.")
  @Size(min = 10, max = 250, message = "The email must be between {min} and {max} characters long.")
  private String issuer;
  
  /**
   * The subject (sub) of this OIDC user.
   */
  @NotNull(message = "The subject must not be null.")
  @Size(min = 5, max = 250, message = "The subject must) be between {min} and {max} characters long.")
  private String sub;
  
  
  @Transient
  @JsonIgnore
  public String getDisplayText() {
    return identity.getDisplayText();
  }
  
  @Transient
  @JsonIgnore
  public String getUri() {
    return identity.getUri();
  }
  
  public DisplayableName getName() {
    return identity.getName();
  }
}
