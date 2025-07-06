package auth.papertrail.app.service.implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.dto.Details;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.AuthInfoRepository;
import auth.papertrail.app.request.ConfirmRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.ConfirmationService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class iConfirmationService implements ConfirmationService {

    private final PasswordEncoder encoder;

    private final AuthInfoRepository authInfoRepository;

    @Autowired
    public iConfirmationService(PasswordEncoder encoder, AuthInfoRepository authInfoRepository) {
        this.encoder = encoder;
        this.authInfoRepository = authInfoRepository;
    }

    public AuthResponse confirmationProcess(HttpServletRequest servletRequest, ConfirmRequest request) {
        UUID id = getUuid(servletRequest.getAttribute("id").toString());
        checkUserStatus(id);
        updatePassword(id, encodePassword(request));
        return new AuthResponse(ResponseCode.CONFIRM_OK, Details.NONE);
    }

    private String encodePassword(ConfirmRequest request) {
        return encoder.encode(request.getPassword());
    }

    private UUID getUuid(String id) {
        return UUID.fromString(id);
    }

    @Transactional
    private void checkUserStatus(UUID id) {
        UserStatus status = authInfoRepository.findById(id).orElseThrow(
            () -> new AuthException(ExceptionType.USER_NOT_FOUND, Details.NONE)
        ).getUserStatus();
        if (UserStatus.CONFIRMED == status) {
            throw new AuthException(ExceptionType.USER_CONFIRMED, Details.NONE);
        }
    }

    @Transactional
    private void updatePassword(UUID id, String password) {
        authInfoRepository.updatePasswordAndStatus(id, password, UserStatus.CONFIRMED);
    }

}
