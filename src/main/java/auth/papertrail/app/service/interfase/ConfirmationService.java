package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.ConfirmRequest;
import auth.papertrail.app.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ConfirmationService {

    public AuthResponse confirmationProcess(HttpServletRequest servletRequest, ConfirmRequest request);

}
