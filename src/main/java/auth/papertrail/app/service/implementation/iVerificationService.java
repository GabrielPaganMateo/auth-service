package auth.papertrail.app.service.implementation;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.response.AuthResponse;
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

    public AuthResponse verificationProcess(VerifyRequest request) {
        UUID id = verifyTokenGetId(request.getToken());
        EndUser user = getUser(id);
        checkUserAlreadyVerified(user);
        String token = generateRegistrationToken(user);
        return new AuthResponse(ResponseCode.VERIFY_OK, Map.of("email", user.getEmail(), "token", token));
    }

    private void checkUserAlreadyVerified(EndUser user) {
         if (user.getAuthInfo().getUserStatus() == UserStatus.CONFIRMED) {
            throw new AuthException(ExceptionType.USER_EXISTS, Details.email(user.getEmail()));
        }
    }

    @Transactional(readOnly = true) 
    EndUser getUser(UUID id) {
            return userRepository.findById(id).orElseThrow(
                () -> new AuthException(ExceptionType.USER_NOT_FOUND, Details.NONE)
            );
    }

    private UUID verifyTokenGetId(String token) {
        return UUID.fromString(jwtService.verifyToken(token, TokenType.VERIFICATION).getSubject());
    }

    private String generateRegistrationToken(EndUser user) {
        return jwtService.createToken(user, TokenType.CONFIRMATION);
    }

}
