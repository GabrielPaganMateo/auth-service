package auth.papertrail.app.service.implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.AuthInfoRepository;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.ConfirmRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.ConfirmationService;
import auth.papertrail.app.service.interfase.JWTService;
import auth.papertrail.app.service.utilities.AuthServiceUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class iConfirmationService implements ConfirmationService {

    private final PasswordEncoder encoder;

    private final AuthInfoRepository authInfoRepository;

    private final JWTService jwtService;

    private final UserRepository endUserRepository;

    @Autowired
    public iConfirmationService(PasswordEncoder encoder, AuthInfoRepository authInfoRepository, JWTService jwtService, UserRepository endUserRepository) {
        this.encoder = encoder;
        this.authInfoRepository = authInfoRepository;
        this.jwtService = jwtService;
        this.endUserRepository = endUserRepository;
    }

    public AuthResponse confirmationProcess(HttpServletRequest servletRequest, ConfirmRequest request, HttpServletResponse servletResponse) {
        UUID id = getUuid(servletRequest.getAttribute("id").toString());
        EndUser user = checkUserStatus(id);
        updatePassword(id, encodePassword(request));
        String token = generateLoginToken(user);
        AuthServiceUtils.setAuthHeader(servletResponse, token);
        return new AuthResponse(ResponseCode.CONFIRM_OK, Details.NONE);
    }

    private String encodePassword(ConfirmRequest request) {
        return encoder.encode(request.getPassword());
    }

    private UUID getUuid(String id) {
        return UUID.fromString(id);
    }

    @Transactional
    private EndUser checkUserStatus(UUID id) {
        EndUser user = endUserRepository.findById(id).orElseThrow(
            () -> new AuthException(ExceptionType.USER_NOT_FOUND, Details.NONE)
        );
        if (UserStatus.CONFIRMED == user.getAuthInfo().getUserStatus()) {
            throw new AuthException(ExceptionType.USER_CONFIRMED, Details.NONE);
        }
        return user;
    }

    @Transactional
    private void updatePassword(UUID id, String password) {
        authInfoRepository.updatePasswordAndStatus(id, password, UserStatus.CONFIRMED);
    }

    private String generateLoginToken(EndUser user) {
        return jwtService.createToken(user, TokenType.LOGIN, Details.NONE);
    }

}
