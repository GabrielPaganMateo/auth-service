package auth.papertrail.app.service.interfase;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface JWTService {

    public String createToken();

    public DecodedJWT verifyToken(String token);
}
