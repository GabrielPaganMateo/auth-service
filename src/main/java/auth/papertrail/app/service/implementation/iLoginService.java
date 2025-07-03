package auth.papertrail.app.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import auth.papertrail.app.entity.EndUser;
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

        return new AuthResponse(null, null);
    }

    // private EndUser getUser(LoginRequest request) {
    //     Optional<EndUser> oUser = userRepository.findByEmail(request.getEmail());
    //     EndUser user = oUser.orElseThrow(throw new AuthException(null, null));
    //     if (user.orElseThrow(null)) {

    //     }
    //     encoder.matches(null, null)
    // }

}
