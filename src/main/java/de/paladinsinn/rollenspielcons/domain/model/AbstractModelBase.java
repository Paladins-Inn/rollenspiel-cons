package de.paladinsinn.rollenspielcons.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import java.beans.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


/**
 * A base entity with an ID and display names.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@Jacksonized
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public class AbstractModelBase implements HasId, HasDisplayText {
  /**
   * The unique identifier of this entity.
   */
  private long id;
  
  /**
   * The name of this entity.
   */
  private DisplayableName name;
  
  
  @Override
  @JsonIgnore
  @Transient
  public String getDisplayText() {
    return name.getDisplayText();
  }
  
  @Override
  @JsonIgnore
  @Transient
  public String getUri() {
    return name.getUri();
  }
}
