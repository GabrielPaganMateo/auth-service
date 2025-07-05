package auth.papertrail.app.service.implementation;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.exception.VerifyException;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.RedirectService;

@Service
public class iRedirectService implements RedirectService {

    @Value("${auth-service.redirect.verification}")
    private String url;

    public ModelAndView verifyRedirect(AuthResponse response) {
        String parameters = "?email=%s&token=%s";
        String email = response.getDetails().get(MapKeys.EMAIL);
        String token = response.getDetails().get(MapKeys.TOKEN);
        String formattedParams = String.format(parameters, 
            UriUtils.encode(email, StandardCharsets.UTF_8), 
            UriUtils.encode(token, StandardCharsets.UTF_8)
        );
        StringBuilder buildUrl = new StringBuilder(url);
        buildUrl.append(formattedParams);
        return new ModelAndView("redirect:" + buildUrl.toString());
    }

    public ModelAndView exceptionRedirect(VerifyException exception) {
        String parameters;
        String email;
        String code;
        String formattedParams = "";
        if (ExceptionType.USER_EXISTS == exception.getExceptionType()) {
            parameters = "?email=%s&code=%s";
            email = exception.getDetails().get(MapKeys.EMAIL);
            code = exception.getExceptionType().getCode();
            formattedParams = String.format(parameters, 
                UriUtils.encode(email, StandardCharsets.UTF_8), 
                UriUtils.encode(code, StandardCharsets.UTF_8)
            );
        } else if (ExceptionType.USER_NOT_FOUND == exception.getExceptionType()) {
            parameters = "?code=%s";
            email = null;
            code = exception.getExceptionType().getCode();
            formattedParams = String.format(parameters,
                UriUtils.encode(code, StandardCharsets.UTF_8)
            );
        }
        StringBuilder buildUrl = new StringBuilder(url);
        buildUrl.append(formattedParams);
        return new ModelAndView("redirect:" + buildUrl.toString());
    }

}
