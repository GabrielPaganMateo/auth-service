package auth.papertrail.app.service.implementation;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.interfaces.DecodedJWT;

import auth.papertrail.app.constants.TokenClaims;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.response.VerifyResponse;
import auth.papertrail.app.service.interfase.JWTService;
import auth.papertrail.app.service.interfase.VerificationService;

@Service
public class iVerificationService implements VerificationService {

    private final JWTService jwtService;

    private final UserRepository userRepository;

    public iVerificationService(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public VerifyResponse verificationProcess(VerifyRequest request) {
        UUID id = verifyTokenGetId(request.getToken());
        EndUser user = getUser(id);
        checkUserAlreadyVerified(user);
        changeUserStatus(user);
        String token = generateRegistrationToken(user);
        return new VerifyResponse(ResponseCode.VERIFY_OK.getCode(), ResponseCode.VERIFY_OK.getMessage(), Map.of("email", user.getEmail(), "token", token));
    }

    private void checkUserAlreadyVerified(EndUser user) {
        if (user.getAuthInfo().getUserStatus() == UserStatus.VERIFIED) {
            throw new AuthException(ExceptionType.USER_EXISTS, Details.email(user.getEmail()));
        }
    }

    @Transactional(readOnly = true) 
    EndUser getUser(UUID id) {
        try {
            return userRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new AuthException(ExceptionType.USER_NOT_FOUND, Details.NONE);
        }
    }

    @Transactional
    private void changeUserStatus(EndUser user) {
        user.getAuthInfo().setUserStatus(UserStatus.VERIFIED);
        userRepository.save(user);
    }

    private UUID verifyTokenGetId(String token) {
        return UUID.fromString(jwtService.verifyToken(token, TokenType.VERIFICATION).getSubject());
    }

    private String generateRegistrationToken(EndUser user) {
        return jwtService.createToken(user, TokenType.REGISTRATION);
    }

}
