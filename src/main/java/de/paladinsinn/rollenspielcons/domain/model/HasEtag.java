package de.paladinsinn.rollenspielcons.domain.model;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 *
 * @author klenkes74
 * @since 21.09.25
 */
public interface HasEtag {
  String monitoredData();
  
  default String getETag() throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(monitoredData().getBytes());
    
    byte[] digest = md.digest();
    StringBuilder sb = new StringBuilder("\"");
    
    for (byte b : digest) {
      sb.append(String.format("%02x", b & 0xff));
    }
    
    return sb.append("\"").toString();
  }
}
