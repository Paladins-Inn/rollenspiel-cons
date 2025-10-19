package de.paladinsinn.rollenspielcons.services.security.model;


import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-02
 */
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ResourceRepresentation {
  private String name;
  private String type;
  private String owner; // userId/sub
  private Boolean ownerManagedAccess;
  private List<String> uris;
  private List<String> scopes;
  private Map<String, List<String>> attributes;
}
