package auth.papertrail.app.service.interfase;

import auth.papertrail.app.request.VerifyRequestV2;
import auth.papertrail.app.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VerificationService {

    public AuthResponse verificationProcess(VerifyRequestV2 request, HttpServletRequest servletRequest, HttpServletResponse servletResponse);

    public AuthResponse resendVerificationCode(HttpServletRequest servletRequest);

}
