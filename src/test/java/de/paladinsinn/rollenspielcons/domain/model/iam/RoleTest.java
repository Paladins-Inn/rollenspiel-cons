package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import de.paladinsinn.rollenspielcons.domain.api.iam.Role;
import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@XSlf4j
public class RoleTest {
  static final IamGenerator GENERATOR = new IamGenerator();
  
  @Test
  public void shouldBeAValidGroupWhenCalledWithoutOwner() {
    log.entry();
    
    Role result = GENERATOR.generateRole(1L, "role");
    
    assertEquals(1L, result.getId());
    assertEquals("role", result.getDisplayText());
    assertNull(result.getOwner());
    
    log.exit();
  }
  
  @Test
  public void shouldBeAValidGroupWhenCalledWithOwner() {
    log.entry();
    
    Owner owner  = GENERATOR.generateIdentity(2L, "identity", "https://issuer", "123451234512345");
    Role  result = GENERATOR.generateRole(1L, "role", owner);
    
    assertEquals(1L, result.getId());
    assertEquals("role", result.getDisplayText());
    assertEquals(owner, result.getOwner());
    
    log.exit();
  }
  
  @Test
  public void shouldBeNotEqualGroupsWhenCalledWithDifferentId() {
    log.entry();
    
    Role result = GENERATOR.generateRole(1L, "role");
    Role other = GENERATOR.generateRole(2L, "other");
    
    assertNotEquals(result, other);
    
    log.exit();
  }
}
