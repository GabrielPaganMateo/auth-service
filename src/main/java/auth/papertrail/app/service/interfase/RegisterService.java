package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.RegisterRequest;
import auth.papertrail.app.response.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface RegisterService {

    public AuthResponse registrationProcess(RegisterRequest registerRequest, HttpServletResponse response);
    
}
