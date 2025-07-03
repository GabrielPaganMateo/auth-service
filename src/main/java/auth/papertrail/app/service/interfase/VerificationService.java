package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.response.AuthResponse;

public interface VerificationService {

    public AuthResponse verificationProcess(VerifyRequest request);

}
