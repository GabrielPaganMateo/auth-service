package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.LoginRequest;
import auth.papertrail.app.response.AuthResponse;

public interface LoginService {

    public AuthResponse loginProcess(LoginRequest request);
    
}
