package auth.papertrail.app.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.annotation.AuthRequired;
import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.service.interfase.JWTService;
import jakarta.persistence.MapKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JWTService jwtService;

    @Autowired
    public AuthInterceptor(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            AuthRequired metadata = method.getMethodAnnotation(AuthRequired.class);

            if (metadata == null) {
                return true;
            } else {
                String header = request.getHeader(MapKeys.AUTH_HEADER);
                if (header == null || header.startsWith(MapKeys.BEARER) == false) {
                    throw new AuthException(ExceptionType.INVALID_HEADER, Details.NONE);
                }
                String token = header.substring(7, header.length());
                DecodedJWT jwt = jwtService.verifyToken(token, metadata.requires());
                request.setAttribute(MapKeys.ID, jwt.getSubject());
                return true;
            }
        }
        return true;
    }

}
