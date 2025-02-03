package user.gateway.auth_service.Services;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import user.gateway.auth_service.Constants.VerifyMessages;
import user.gateway.auth_service.Entities.Token;
import user.gateway.auth_service.Entities.User;
import user.gateway.auth_service.Enums.ExceptionsEnum;
import user.gateway.auth_service.Enums.TokenTypeEnum;
import user.gateway.auth_service.Enums.VerifiedStatusEnum;
import user.gateway.auth_service.Exceptions.VerificationException;
import user.gateway.auth_service.Feigns.UserFeign;
import user.gateway.auth_service.Responses.VerifyResponse;

@Service
public class VerifyService {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserFeign userFeign;

    public VerifyResponse verifyUser(String token) throws Exception {
        return formatResponse(changeStatus(checkStatus((checkEmail(checkExpirationDate(checkTokenType(decodeJWT(token))))))));
    }

    public Token decodeJWT(String token) throws VerificationException {
        try {
            return jwtService.decodeJWT(token);
        } catch (ExpiredJwtException exception) {
            throw new VerificationException(ExceptionsEnum.EXPIRED_TOKEN);
        }
    }

    private Token checkTokenType(Token token) throws Exception {
        if (token.getType() == TokenTypeEnum.EMAIL_VERIFICATION.getTypeCode()) {
            return token;
        }
        throw new VerificationException(ExceptionsEnum.INCORRECT_TOKEN_TYPE);
    }

    private Token checkExpirationDate(Token token) throws VerificationException {
        if (token.getExpiration().before(new Date()) == false) {
            return token;
        }
        throw new VerificationException(ExceptionsEnum.EXPIRED_TOKEN);
    }

    private User getUser(Token token) throws VerificationException {
        ResponseEntity<User> userResponseEntity = userFeign.getUser(token.getSubject());
        if (userResponseEntity.getStatusCode() == HttpStatus.OK) {
            return userResponseEntity.getBody();
        }
        throw new VerificationException(ExceptionsEnum.USER_LOOKUP_FAILED);
    }

    private User checkEmail(Token token) throws VerificationException {
        User user = getUser(token);
        if (token.getEmail().equals(user.getEmail())) {
            return user;
        }
        throw new VerificationException(ExceptionsEnum.EMAIL_MATCH_FAIL);
    }

    private User checkStatus(User user) throws VerificationException {
        if(user.getVerified().equals(VerifiedStatusEnum.UNVERIFIED)) {
            return user;
        }
        throw new VerificationException(ExceptionsEnum.IS_VERIFIED);
    }

    private String changeStatus(User user) throws VerificationException {
        user.setVerified(VerifiedStatusEnum.VERIFIED);
        if(userFeign.registerUser(user).getStatusCode() == HttpStatus.OK) {
            return user.getEmail();
        }
        throw new VerificationException(ExceptionsEnum.VERIFY_FAILED);
    }

    private VerifyResponse formatResponse(String email) {
        String message = String.format(VerifyMessages.VERIFIED, email);
        return new VerifyResponse(message);
    }
}
