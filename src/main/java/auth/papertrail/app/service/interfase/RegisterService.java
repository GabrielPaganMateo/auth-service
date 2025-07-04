package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.RegisterRequest;
import auth.papertrail.app.response.AuthResponse;

public interface RegisterService {

    public AuthResponse registrationProcess(RegisterRequest registerRequest);
    
}
