package de.paladinsinn.rollenspielcons.config;


import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;



/**
 *
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2025-11-02
 */
public final class InsecureTls {

  private InsecureTls() {}

  public static void disable() {
    try {
      TrustManager[] trustAll = new TrustManager[] {
        new X509TrustManager() {
          public void checkClientTrusted(X509Certificate[] chain, String authType) {}
          public void checkServerTrusted(X509Certificate[] chain, String authType) {}
          public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }
      };

      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAll, new SecureRandom());

      // JVM-weit
      SSLContext.setDefault(sc);

      // Für HttpsURLConnection
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      HttpsURLConnection.setDefaultHostnameVerifier((h, s) -> true);

      // Für java.net.http.HttpClient Hostnamenprüfung ausschalten (intern, nur Test)
      System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
    } catch (Exception e) {
      throw new IllegalStateException("TLS-Verifikation konnte nicht deaktiviert werden", e);
    }
  }
}