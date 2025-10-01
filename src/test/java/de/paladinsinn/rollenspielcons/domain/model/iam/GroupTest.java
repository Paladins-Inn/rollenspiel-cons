package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Group;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
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
public class GroupTest {
  static final IamGenerator GENERATOR = new IamGenerator();
  
  @Test
  public void shouldBeAValidGroupWhenCalledWithoutOwner() {
    log.entry();
    
    Group result = GENERATOR.generateGroup(1L, "group");
    
    assertEquals(1L, result.getId());
    assertEquals("group", result.getDisplayText());
    assertNull(result.getOwner());
    
    log.exit();
  }
  
  @Test
  public void shouldBeAValidGroupWhenCalledWithOwner() {
    log.entry();
    
    Owner owner  = GENERATOR.generateIdentity(2L, "identity", "https://issuer", "123451234512345");
    Group result = GENERATOR.generateGroup(1L, "group", owner);
    
    assertEquals(1L, result.getId());
    assertEquals("group", result.getDisplayText());
    assertEquals(owner, result.getOwner());
    
    log.exit();
  }
  
  @Test
  public void shouldBeNotEqualGroupsWhenCalledWithDifferentId() {
    log.entry();
    
    Group result = GENERATOR.generateGroup(1L, "group");
    Group other = GENERATOR.generateGroup(2L, "other");
    
    assertNotEquals(result, other);
    
    log.exit();
  }
}
