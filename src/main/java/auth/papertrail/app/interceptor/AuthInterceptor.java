package auth.papertrail.app.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.annotation.AuthRequired;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.service.interfase.JWTService;
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
        System.out.println("Hello there");
        System.out.println(handler.getClass());
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            AuthRequired metadata = method.getMethodAnnotation(AuthRequired.class);

            if (metadata == null) {
                System.out.println("Hello there 1");
                return true;
            } else {
                String header = request.getHeader("Authorization");
                if (header == null || header.startsWith("Bearer ") == false) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                    System.out.println("Hello there 2");
                    return false;
                }
                String token = header.substring(7, header.length());
                DecodedJWT jwt = jwtService.verifyToken(token, metadata.requires());
                request.setAttribute("id", jwt.getToken());
                System.out.println("Hello there 3");
                return true;
            }
        }
         System.out.println("Hello there 4");
        return false;
    }

}
