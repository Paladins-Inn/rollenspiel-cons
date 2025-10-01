package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Action;
import de.paladinsinn.rollenspielcons.domain.api.iam.Group;
import de.paladinsinn.rollenspielcons.domain.api.iam.Identity;
import de.paladinsinn.rollenspielcons.domain.api.iam.OidcUser;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import de.paladinsinn.rollenspielcons.domain.api.iam.Permission;
import de.paladinsinn.rollenspielcons.domain.api.iam.Role;
import de.paladinsinn.rollenspielcons.domain.model.DisplayableName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.extern.slf4j.XSlf4j;


/**
 * Generates IAM model entities for unit tests.
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-01
 */
@XSlf4j
public class IamGenerator {
  public IdentityImpl generateIdentity(
      @Min(value = 1, message = "The id has to be at least {value}") final long id,
      @NotNull @NotBlank final String name,
      @NotNull @NotBlank final String issuer,
      @NotNull @NotBlank final String sub
  ) {
    log.entry(id, name, issuer, sub);
    
    IdentityImpl result = IdentityImpl.builder()
                                      .id(id)
                                      .name(
            generateDisplayText(name)
        )
                                      .build();
    
    result.getOidcUsers().add(generateOidc(result, issuer, sub));
    
    return log.exit(result);
  }
  
  private DisplayableName generateDisplayText(@NotNull @NotBlank final String name) {
    log.entry(name);
    
    DisplayableName result = DisplayableName.builder()
        .name(name)
        .build();
    
    return log.exit(result);
  }
  private DisplayableName generateDisplayText(
      @NotNull @NotBlank final String name,
      @NotNull @NotBlank final String uri
  ) {
    log.entry(name, uri);
    
    DisplayableName result = DisplayableName.builder()
        .name(name)
        .uri(uri)
        .build();
    
    return log.exit(result);
  }
  
  public OidcUser generateOidc(
      @NotNull final Identity identity,
      @NotNull @NotBlank final String issuer,
      @NotNull @NotBlank final String sub
  ) {
    log.entry(identity, issuer, sub);
    
    OidcUser result = OidcUserImpl
        .builder()
        .id(identity.getId())
        .issuer(issuer)
        .sub(sub)
        .build();
    
    return log.exit(result);
  }
  
  public Group generateGroup(
      @NotNull final long id,
      @NotNull final String name
  ) {
    log.entry(id, name);
    
    Group result = GroupImpl
        .builder()
        .id(id)
        .name(generateDisplayText(name))
        .build();
    
    return log.exit(result);
  }

  public Group generateGroup(
      @NotNull final long id,
      @NotNull final String name,
      @NotNull final Owner owner
  ) {
    log.entry(id, name, owner);
    
    Group result = GroupImpl
        .builder()
        .id(id)
        .name(generateDisplayText(name))
        .owner(owner)
        .build();
    
    return log.exit(result);
  }
  
  public Owner generateOwner(
      @NotNull final long id,
      @NotNull @NotBlank final String name,
      @NotNull final Set<Group> groups
      ) {
    log.entry(id, name, name);
    
    Owner result = GroupImpl
        .builder()
        .id(id)
        .name(DisplayableName.builder().name(name).build())
        .build();
    
    return log.exit(result);
  }
  
  public Role generateRole(
      @NotNull final long id,
      @NotNull @NotBlank final String name
  ) {
    log.entry(id, name);
    
    Role result = RoleImpl
        .builder()
        .id(id)
        .name(generateDisplayText(name))
        .build();
    
    return log.exit(result);
  }
  
  public Role generateRole(
      @NotNull final long id,
      @NotNull @NotBlank final String name,
      @NotNull final Owner owner
  ) {
    log.entry(id, name);
    
    Role result = RoleImpl
        .builder()
        .id(id)
        .name(generateDisplayText(name))
        .owner(owner)
        .build();
    
    return log.exit(result);
  }
  
  public Permission generateRule(
    @NotNull final long id,
    @NotNull @NotBlank final String name,
    @NotNull final Action action,
    @NotNull @NotBlank final String object
  ) {
    log.entry(id, name, action, object);
    
    Permission result = PermissionImpl
        .builder()
        .id(id)
        .name(generateDisplayText(name))
        .action(action)
        .object(object)
        .build();
    
    return log.exit(result);
  }
}
