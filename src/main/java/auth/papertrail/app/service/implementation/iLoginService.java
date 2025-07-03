package auth.papertrail.app.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.LoginRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.LoginService;

@Service
public class iLoginService implements LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public iLoginService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public AuthResponse loginProcess(LoginRequest request) {
        EndUser user = getUser(request);
        checkUserStatus(user);
        passwordMatch(user, request);
        //generateLoginJWT
        return new AuthResponse(null, null);
    }

    @Transactional(readOnly = true)
    private EndUser getUser(LoginRequest request) {
        Optional<EndUser> oUser = userRepository.findByEmail(request.getEmail());
        return oUser.orElseThrow(
            () -> new AuthException(ExceptionType.USER_NOT_FOUND, Details.email(request.getEmail()))
        );
    }

    private void checkUserStatus(EndUser user) {
        if (user.getAuthInfo().getUserStatus() == UserStatus.REGISTERED) {
            throw new AuthException(ExceptionType.USER_UNVERIFIED, Details.email(user.getEmail()));
        }
    }

    private void passwordMatch(EndUser user, LoginRequest request) {
        boolean match = encoder.matches(user.getAuthInfo().getPassword(), request.getPassword());
        if (match == false) {
            throw new AuthException(ExceptionType.USER_NOT_FOUND, Details.email(request.getEmail()));
        }
    }

}
