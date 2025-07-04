package auth.papertrail.app.service.interfase;

import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.TokenType;

public interface JWTService {

    public String createToken(EndUser user, TokenType type);

    public DecodedJWT verifyToken(String token, TokenType type);
}
