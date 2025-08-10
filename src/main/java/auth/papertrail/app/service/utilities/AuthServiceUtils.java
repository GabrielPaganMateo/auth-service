package auth.papertrail.app.service.utilities;

import org.apache.commons.validator.routines.EmailValidator;

import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.exception.AuthException;
import jakarta.servlet.http.HttpServletResponse;

public class AuthServiceUtils {

    public static void setAuthHeader(HttpServletResponse servletResponse, String token) {
        servletResponse.addHeader(MapKeys.AUTH_HEADER, MapKeys.BEARER + token);
    }

    public static void validateEmailFormat(String email) {
        if(EmailValidator.getInstance().isValid(email) == false) {
            throw new AuthException(ExceptionType.INVALID_EMAIL, Details.email(email));
        }
    }
}
