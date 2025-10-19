package de.paladinsinn.rollenspielcons.domain.api;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.beans.Transient;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The interface for classes which should provide an ETag.
 *
 * @author klenkes74
 * @since 21.09.25
 */
public interface HasEtag {
  /**
   * Generates an MD5 hash over the data provided by {@link #monitoredData()}.
   *
   * @return The ETag as hex-string.
   * @throws NoSuchAlgorithmException if MD5 is not supported by the JVM.
   */
  default String getETag() throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(monitoredData().getBytes());
    
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder();
    
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }
    
    return sb.toString();
  }
  
  /**
   * Data for generating the ETag with.
   *
   * @return String with the concatenated data.
   */
  @JsonIgnore
  default String monitoredData() {
    return toString();
  }
  
}
