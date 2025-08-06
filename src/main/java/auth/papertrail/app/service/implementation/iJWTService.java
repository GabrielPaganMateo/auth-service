package auth.papertrail.app.service.implementation;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.constants.TokenClaims;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.service.interfase.JWTService;

@Service
public class iJWTService implements JWTService {

    private final Algorithm algorithm;

    @Autowired
    public iJWTService(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String createToken(EndUser user, TokenType type, Map<String, String> details) {
        return JWT.create()
            .withIssuer(TokenClaims.ISSUER)
            .withClaim(TokenClaims.TYPE, type.getCode())
            .withSubject(user.getId().toString())
            .withClaim(TokenClaims.DETAILS, details)
            .withExpiresAt(Instant.now().plusSeconds(type.getTimeToLive()))
            .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token, TokenType type) {
        try {
            return JWT.require(algorithm).withIssuer(TokenClaims.ISSUER).withClaim(TokenClaims.TYPE, type.getCode()).build().verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            if (e instanceof TokenExpiredException) {
                throw new AuthException(ExceptionType.EXPIRED_TOKEN, Details.NONE);
            } else {
                throw new AuthException(ExceptionType.INVALID_TOKEN, Details.NONE);
            }
        }
    }

}
