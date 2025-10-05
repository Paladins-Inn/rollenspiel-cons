package de.paladinsinn.rollenspielcons.ui;

import de.paladinsinn.rollenspielcons.services.gcalendar.GoogleCalendarService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@XSlf4j
public class HomeController extends AbstractBaseController {
  
  private final GoogleCalendarService googleCalendarService;

  @PermitAll
  @GetMapping
  @Counted(value = "calendar_count", description = "Total calls to the calender display page")
  @Timed(value = "calendar_duration", description = "Time of calls to the calender display page", percentiles = {0.5, 0.95, 0.99})
  public String calendar(@AuthenticationPrincipal OAuth2User user, Model model) {
    log.entry(user, model);
    
    return forwarder(user, model, "pages/calendar");
  }
  
  @PermitAll
  @GetMapping("/api/events")
  @ResponseBody
  @Counted(value = "api_events_count", description = "Total calls to event listing API")
  @Timed(value = "api_events_duration", description = "Duration of calls to event listing API", percentiles = {0.5, 0.95, 0.99})
  public List<Map<String, Object>> getEvents(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
    log.entry(start, end);

    LocalDateTime startDate = start != null ? start : LocalDateTime.now().minusMonths(1);
    LocalDateTime endDate = end != null ? end : LocalDateTime.now().plusMonths(3);
    
    return log.exit(googleCalendarService.getEvents(startDate, endDate));
  }
}