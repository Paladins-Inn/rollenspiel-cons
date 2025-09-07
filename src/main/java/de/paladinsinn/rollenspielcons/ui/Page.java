package de.paladinsinn.rollenspielcons.ui;


import cn.ipokerface.snowflake.SnowflakeIdGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;


/**
 *
 *
 * @author klenkes74
 * @since 07.09.25
 */
@Builder(toBuilder = true, setterPrefix = "")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString(includeFieldNames = true)
@EqualsAndHashCode(of = {"id"})
public class Page {
  @Builder.Default
  private UUID id = UUID.randomUUID();
  
  private String title;
  private String description;
}
