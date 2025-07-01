package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.response.VerifyResponse;

public interface VerificationService {

    public VerifyResponse verificationProcess(VerifyRequest request);

}
