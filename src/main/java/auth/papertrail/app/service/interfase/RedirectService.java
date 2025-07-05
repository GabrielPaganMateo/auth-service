package auth.papertrail.app.service.interfase;

import org.springframework.web.servlet.ModelAndView;

import auth.papertrail.app.exception.VerifyException;
import auth.papertrail.app.response.AuthResponse;

public interface RedirectService {

    public ModelAndView verifyRedirect(AuthResponse response);

    public ModelAndView exceptionRedirect(VerifyException exception);

}
