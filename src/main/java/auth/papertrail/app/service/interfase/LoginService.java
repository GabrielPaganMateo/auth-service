package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.LoginRequest;
import auth.papertrail.app.response.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

    public AuthResponse loginProcess(HttpServletResponse servletResponse, LoginRequest request);
    
}
