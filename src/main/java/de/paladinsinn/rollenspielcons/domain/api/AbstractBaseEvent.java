package de.paladinsinn.rollenspielcons.domain.api;


import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-26
 */
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractBaseEvent {
  @Builder.Default
  private final UUID id = UUID.randomUUID();
}
