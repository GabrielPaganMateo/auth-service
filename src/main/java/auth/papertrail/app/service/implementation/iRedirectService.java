package auth.papertrail.app.service.implementation;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.exception.VerifyException;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.RedirectService;

@Service
public class iRedirectService implements RedirectService {

    @Value("${auth-service.redirect.verification}")
    private String url;

    public ModelAndView verifyRedirect(AuthResponse response) {
        String parameters = "?response=%s";
        String formattedParams = String.format(parameters, 
            UriUtils.encode(response.getDetails().get(MapKeys.TOKEN), StandardCharsets.UTF_8)
        );
        StringBuilder buildUrl = new StringBuilder(url);
        buildUrl.append(formattedParams);
        return new ModelAndView("redirect:" + buildUrl.toString());
    }

    public ModelAndView exceptionRedirect(VerifyException exception) {
        String parameters = "?response=%s";
        String code = exception.getExceptionType().getCode();
        String formattedParams = String.format(parameters,  
            UriUtils.encode(code, StandardCharsets.UTF_8)
        );
        StringBuilder buildUrl = new StringBuilder(url);
        buildUrl.append(formattedParams);
        return new ModelAndView("redirect:" + buildUrl.toString());
    }

}
