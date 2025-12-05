/*
 * Copyright (c) 2025. Roland T. Lichti, Kaiserpfalz EDV-Service
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
