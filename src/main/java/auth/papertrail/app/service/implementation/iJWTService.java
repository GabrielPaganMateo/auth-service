package auth.papertrail.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.service.interfase.JWTService;

@Service
public class iJWTService implements JWTService {

    Algorithm algorithm;

    @Autowired
    public iJWTService(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String createToken() {
        return JWT.create().withIssuer("auth-service").sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(algorithm).withIssuer("auth-service").build().verify(token);
    }

}
