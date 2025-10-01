package de.paladinsinn.rollenspielcons.domain.model.iam;


import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@XSlf4j
public class IdentityTest {
  static final IamGenerator GENERATOR = new IamGenerator();
  
  @Test
  public void shouldBeAValidIdentityWhenCalledWithoutGroupAndRoles() {
    log.entry();
    
    Identity result = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    
    assertEquals(1L, result.getId());
    assertEquals("identity", result.getDisplayText());
    assertEquals(1, result.getOidcUsers().size());
    assertEquals(0, result.getGroups().size());
    assertEquals(0, result.getRoles().size());
    
    log.exit();
  }
  
  @Test
  public void shouldBeAValidIdentityWhenCalledWithGroupAndWithoutRoles() {
    log.entry();
    
    Identity result = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    Group group = GENERATOR.generateGroup(2L, "group", result);
    result.getGroups().add(group);
    
    assertEquals(1L, result.getId());
    assertEquals("identity", result.getDisplayText());
    assertEquals(1, result.getGroups().size());
    assertEquals(0, result.getRoles().size());
    
    log.exit();
  }
  
  @Test
  public void shouldBeAValidIdentityWhenCalledWithRolesAndWithoutGroups() {
    log.entry();
    
    Identity result = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    Role role = GENERATOR.generateRole(3L, "role", result);
    result.getRoles().add(role);
    
    assertEquals(1L, result.getId());
    assertEquals("identity", result.getDisplayText());
    assertEquals(0, result.getGroups().size());
    assertEquals(1, result.getRoles().size());
    
    log.exit();
  }

  @Test
  public void shouldBeAValidIdentityWhenCalledWithRolesAndGroups() {
    log.entry();
    
    Identity result = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    Group group = GENERATOR.generateGroup(2L, "group", result);
    result.getGroups().add(group);
    Role role = GENERATOR.generateRole(3L, "role", result);
    result.getRoles().add(role);
    
    assertEquals(1L, result.getId());
    assertEquals("identity", result.getDisplayText());
    assertEquals(1, result.getGroups().size());
    assertEquals(1, result.getRoles().size());
    
    log.exit();
  }
}
