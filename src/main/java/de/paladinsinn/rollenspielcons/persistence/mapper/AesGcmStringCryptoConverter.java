package de.paladinsinn.rollenspielcons.persistence.mapper;


import jakarta.annotation.PostConstruct;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-10-12
 */
@Converter
@Slf4j
public class AesGcmStringCryptoConverter implements AttributeConverter<String, String> {
  private static final SecureRandom RNG = new SecureRandom();
  private static final int GCM_TAG_BITS = 128;
  private static final int IV_LEN = 12;
  
  @Value("${aes256.key:}")
  private String aes256Key;
  private SecretKeySpec secretKey;
  
  @PostConstruct
  public void init() {
    if (aes256Key == null || aes256Key.isBlank()) {
      log.warn("No AES-256 key configured! Encryption/Decryption will not work!");
    } else {
      byte[] key = Base64.getDecoder().decode(aes256Key);
      secretKey = new SecretKeySpec(key, "AES");
      log.info("AES-256 key configured.");
    }
  }
  
  @Override
  public String convertToDatabaseColumn(String attr) {
    if (attr == null) return null;
    
    try {
      byte[] iv = new byte[IV_LEN];
      RNG.nextBytes(iv);
      Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
      c.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_BITS, iv));
      byte[] ct = c.doFinal(attr.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      // Format: base64(iv || ct)
      if (ct.length > Integer.MAX_VALUE - iv.length) {
        throw new IllegalStateException("Output array size would overflow (iv.length + ct.length).");
      }
      byte[] out = new byte[iv.length + ct.length];
      System.arraycopy(iv, 0, out, 0, iv.length);
      System.arraycopy(ct, 0, out, iv.length, ct.length);
      return Base64.getEncoder().encodeToString(out);
    } catch (Exception e) {
      throw new IllegalStateException("Encryption failed", e);
    }
  }
  
  private SecretKeySpec getKeySpec() {
    byte[] key = Base64.getDecoder().decode(aes256Key);
    return new SecretKeySpec(key, "AES");
  }
  
  @Override
  public String convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    
    try {
      byte[] all = Base64.getDecoder().decode(dbData);
      byte[] iv = java.util.Arrays.copyOfRange(all, 0, IV_LEN);
      byte[] ct = java.util.Arrays.copyOfRange(all, IV_LEN, all.length);
      Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
      c.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_BITS, iv));
      byte[] pt = c.doFinal(ct);
      return new String(pt, java.nio.charset.StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new IllegalStateException("Decryption failed", e);
    }
  }
}