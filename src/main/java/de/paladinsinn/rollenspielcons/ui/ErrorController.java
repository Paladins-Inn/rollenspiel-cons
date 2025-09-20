package de.paladinsinn.rollenspielcons.ui;

import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@XSlf4j
public class ErrorController extends AbstractBaseController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        log.entry(request);
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            model.addAttribute("statusCode", statusCode);
            model.addAttribute("errorMessage", errorMessage != null ? errorMessage.toString() : "Unbekannter Fehler");
            model.addAttribute("requestUri", requestUri != null ? requestUri.toString() : "");
            
            // Spezifische Templates für verschiedene HTTP-Status-Codes
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return forwarder(null, model, "pages/error/404");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return forwarder(null, model, "pages/error/403");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return forwarder(null, model, "pages/error/500");
            }
        }
        
        return forwarder(null, model,"pages/error/general");
    }
    
    @RequestMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, 
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        log.entry(error, logout);
        
        if (error != null) {
            model.addAttribute("loginError", true);
            model.addAttribute("errorMessage", "Anmeldung fehlgeschlagen. Bitte versuchen Sie es erneut.");
        }
        
        if (logout != null) {
            model.addAttribute("logoutSuccess", true);
            model.addAttribute("logoutMessage", "Sie wurden erfolgreich abgemeldet.");
        }
        
        return forwarder(null, model, "pages/login");
    }
}
