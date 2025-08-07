package auth.papertrail.app.service.utilities;

import auth.papertrail.app.constants.MapKeys;
import jakarta.servlet.http.HttpServletResponse;

public class AuthServiceUtils {

    public static void setAuthHeader(HttpServletResponse servletResponse, String token) {
        servletResponse.addHeader(MapKeys.AUTH_HEADER, MapKeys.BEARER + token);
    }
}
