package de.paladinsinn.rollenspielcons.services.gcalendar;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import de.paladinsinn.rollenspielcons.domain.api.calendars.Calendar;
import de.paladinsinn.rollenspielcons.domain.model.calendars.GoogleCalendar;
import jakarta.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@XSlf4j
public class GoogleCalenderServiceCreator {
  @Value("${google.client-id:-")
  String clientId;
  @Value("${google.client-secret:-")
  String clientSecret;
  
  /**
   * Verify that the given {@link Calendar} is a {@link GoogleCalendar}.
   *
   * @param gcal The calendar to verify.
   * @throws IllegalArgumentException If the given calendar is not a {@link GoogleCalendar}.
   */
  void verifyGoogleCalendar(@NotNull final Calendar gcal) {
    log.entry(gcal);
    if (!(gcal instanceof GoogleCalendar)) {
      throw log.throwing(new IllegalArgumentException(
          "The importer can only handle google calendars. This calender is of type '"
          + gcal.getSyncType() + "'."));
    }
    
    log.exit("ok");
  }
  
  /**
   * Creates a new {@link com.google.api.services.calendar.Calendar} instance.
   *
   * @param refreshToken The refresh token to use to connect to the Google Calendar API.
   * @return The new {@link com.google.api.services.calendar.Calendar} instance.
   *
   * @throws GeneralSecurityException If the connection to the Google Calendar API fails due to security issues.
   * @throws IOException If there are generic network io issues.
   */
  com.google.api.services.calendar.Calendar createService(
      @NotBlank final String refreshToken
  )
      throws GeneralSecurityException, IOException {
    log.entry("<refresh-token>");
    
    return log.exit(
        new com.google.api.services.calendar.Calendar.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            createHttpRequestInitializerWithRefreshToken(refreshToken)
        )
            .setApplicationName("Rollenspiel Cons Kalender")
            
            .build()
    );
  }
  
  @NotNull
  private HttpRequestInitializer createHttpRequestInitializerWithRefreshToken(
      final String refreshToken
  ) {
    log.entry("<refresh-token>");
    UserCredentials credentials = createUserCredentials(refreshToken);
    
    return log.exit(new HttpCredentialsAdapter(credentials));
  }
  
  @NotNull
  private UserCredentials createUserCredentials(@NotBlank final String refreshToken) {
    log.entry(clientId, "<client-secret>", "<refresh-token>");
    
    return log.exit(
        UserCredentials.newBuilder()
                       .setClientId(clientId)
                       .setClientSecret(clientSecret)
                       .setRefreshToken(refreshToken)
                       .build()
    );
  }
}