package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.RegisterRequest;
import auth.papertrail.app.response.RegisterResponse;

public interface RegisterService {

    public RegisterResponse registrationProcess(RegisterRequest registerRequest);
    
}
