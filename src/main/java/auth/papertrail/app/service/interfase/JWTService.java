package auth.papertrail.app.service.interfase;

import java.util.Map;

import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.TokenType;

public interface JWTService {

    public String createToken(EndUser user, TokenType type, Map<String, String> details);

    public DecodedJWT verifyToken(String token, TokenType type);
}
