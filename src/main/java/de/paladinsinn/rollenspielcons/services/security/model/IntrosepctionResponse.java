package de.paladinsinn.rollenspielcons.services.security.model;


import lombok.AccessLevel;
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
@SuppressWarnings("unused")
@Jacksonized
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
@EqualsAndHashCode
public class IntrosepctionResponse {
  private boolean active;
  private String sub;
  private String aud;
  private String scope;           // bei RPT meist leer; wichtiger sind "permissions"
  private Object permissions;     // kannst du spezifisch modellieren, wenn nötig
  private Long exp;
  private Long iat;
  private Long nbf;
  private String client_id;
  // weitere Felder je nach KC-Version…
}
