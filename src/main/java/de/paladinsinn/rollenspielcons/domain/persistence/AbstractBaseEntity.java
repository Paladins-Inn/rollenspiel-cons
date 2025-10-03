package de.paladinsinn.rollenspielcons.domain.persistence;


import de.paladinsinn.rollenspielcons.domain.api.HasDisplayText;
import de.paladinsinn.rollenspielcons.domain.api.HasId;
import de.paladinsinn.rollenspielcons.domain.api.HasOwner;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.XSlf4j;


/**
 * A base entity with an id, version and a displayable name.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@XSlf4j
public abstract class AbstractBaseEntity implements HasId, HasDisplayText, HasOwner, Serializable {
  private static final long serialVersionUID = 1L;
  
  
  /**
   * The unique identifier of the entity.
   * <p>It is generated using a Snowflake ID generator.</p>
   */
  @Id
  @Column(name = "id", nullable = false, updatable = false)
  @ToString.Include
  @EqualsAndHashCode.Include
  private long id;
  
  /**
   *  The version of the entity for optimistic locking.
   */
  @Version
  @Column(name = "version", nullable = false)
  @Builder.Default
  @ToString.Include
  private int version = 0;

  
  @NotNull
  @Builder.Default
  private EmbeddableOwner embeddableOwner = new EmbeddableOwner();

  @NotNull
  @ToString.Include
  private PersistedDisplayableName name;
  
  
  @Override
  @Transient
  public String getDisplayText() {
    return name != null ? name.getDisplayText() : "";
  }
  
  @Override
  @Transient
  public String getUri() {
    return name != null ? name.getUri() : "";
  }
  
  @Transient
  @SuppressWarnings("unused")
  public String getEmbeddableOwner() {
    return embeddableOwner.getOwner();
  }
  
  @Transient
  @SuppressWarnings("unused")
  public void setEmbeddableOwner(@NotNull final String owner) {
    embeddableOwner.setOwner(owner);
  }
  
  
  /**
   * Ensures that the id is set before persisting the entity.
   * <p>If the id is 0, a new Snowflake ID will be generated and assigned.
   * The factory method {@link SnowflakeIdGenerator#generateId()} is used for generating the id.</p>
   *
   * <p>This is an ugly hack but it seems to be the only JPA compliant way to use the snowflake generator.</p>
   */
  @PrePersist
  private void ensureIdIsSetWithSnowflakeId() {
    log.entry(id);
    
    if (id == 0L) {
      id = SnowflakeIdGenerator.generateId();
    }
    
    log.exit(id);
  }
  
  /**
   * Generates a URI for this entity based on the given base URI and the entity's id.
   *
   * @param baseUri the base URI to use
   * @return the generated URI
   */
  protected String generateUriFromId(final String baseUri) {
    log.entry(baseUri);
    
    return log.exit(baseUri + (baseUri.endsWith("/") ? "" : "/") + id);
  }
}
