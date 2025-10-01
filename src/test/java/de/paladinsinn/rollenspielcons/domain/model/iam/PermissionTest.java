package de.paladinsinn.rollenspielcons.domain.model.iam;


import de.paladinsinn.rollenspielcons.domain.api.iam.Action;
import de.paladinsinn.rollenspielcons.domain.api.iam.HasOwner;
import de.paladinsinn.rollenspielcons.domain.api.iam.Identity;
import de.paladinsinn.rollenspielcons.domain.api.iam.Owner;
import de.paladinsinn.rollenspielcons.domain.api.iam.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-09-30
 */
@XSlf4j
public class PermissionTest {
  static final IamGenerator GENERATOR = new IamGenerator();
  
  @Test
  public void shouldBeAValidRuleWhenCalledWithOwnerAndObject() {
    log.entry();
    
    Identity   owner  = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    TestObject object = TestObject.builder().owner(owner).build();
    Permission result = GENERATOR.generateRule(1L, "rule", Action.CREATE, object.getClass().getCanonicalName());
    
    assertEquals(1L, result.getId());
    assertEquals("rule", result.getDisplayText());
    assertEquals(Action.CREATE, result.getAction());
    assertEquals(Permission.Ruling.ALLOW, result.getRuling());
    
    log.exit();
  }
  
  @Test
  public void shouldAllowRequestWhenCalledWithTheRightObjectActionAndIdentity() {
    log.entry();
    
    Identity owner = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    TestObject object     = TestObject.builder().owner(owner).build();
    Permission permission = GENERATOR.generateRule(1L, "permission", Action.CREATE, object.getClass().getCanonicalName());
    
    assertTrue(permission.apply(owner, Action.CREATE, object));
    
    log.exit();
  }
  
  
  @Test
  public void shouldDenyRequestWhenCalledWithTheRightObjectActionAndIdentity() {
    log.entry();
    
    Identity owner = GENERATOR.generateIdentity(1L, "identity", "https://issuer", "123451234512345");
    Identity user = GENERATOR.generateIdentity(2L, "other identity", "https://issuer", "123451234512345");
    TestObject object     = TestObject.builder().owner(owner).build();
    Permission permission = GENERATOR.generateRule(1L, "permission", Action.CREATE, object.getClass().getCanonicalName());
    
    assertFalse(permission.apply(user, Action.CREATE, object));
    
    log.exit();
  }
  
  @Builder
  @Getter
  public static class TestObject implements HasOwner {
    @NotNull
    private Owner owner;
  }
}
