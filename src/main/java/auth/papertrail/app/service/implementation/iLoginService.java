package auth.papertrail.app.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.LoginRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.JWTService;
import auth.papertrail.app.service.interfase.LoginService;
import auth.papertrail.app.service.interfase.OTPService;
import auth.papertrail.app.service.utilities.AuthServiceUtils;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class iLoginService implements LoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JWTService jwtService;

    private final OTPService otpService;

    @Autowired
    public iLoginService(UserRepository userRepository, PasswordEncoder encoder, JWTService jwtService, OTPService otpService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }

    public AuthResponse loginProcess(HttpServletResponse servletResponse, LoginRequest request) {
        AuthServiceUtils.validateEmailFormat(request.getEmail());
        EndUser user = getUser(request);
        checkUserStatus(user, servletResponse);
        passwordMatch(user, request);
        setAuthHeader(servletResponse, generateLoginToken(user));
        return new AuthResponse(ResponseCode.LOGIN_OK, Details.NONE);
    }

    @Transactional(readOnly = true)
    private EndUser getUser(LoginRequest request) {
        Optional<EndUser> oUser = userRepository.findByEmail(request.getEmail());
        return oUser.orElseThrow(
            () -> new AuthException(ExceptionType.USER_NOT_FOUND, Details.email(request.getEmail()))
        );
    }

    private void checkUserStatus(EndUser user, HttpServletResponse servletResponse) {
        if (user.getAuthInfo().getUserStatus() == UserStatus.REGISTERED) {
            setAuthHeader(servletResponse, generateVerificationToken(user));
            otpService.sendNewOTP(user);
            throw new AuthException(ExceptionType.USER_UNVERIFIED, Details.email(user.getEmail()));
        }
    }

    private void passwordMatch(EndUser user, LoginRequest request) {
        boolean match = encoder.matches(request.getPassword(), user.getAuthInfo().getPassword());
        if (match == false) {
            throw new AuthException(ExceptionType.INVALID_CREDENTIALS, Details.email(request.getEmail()));
        }
    }

    private String generateLoginToken(EndUser user) {
        return jwtService.createToken(user, TokenType.LOGIN, Details.NONE);
    }

    private String generateVerificationToken(EndUser user) {
        return jwtService.createToken(user, TokenType.VERIFICATION, Details.NONE);
    }

    private void setAuthHeader(HttpServletResponse servletResponse, String token) {
        servletResponse.addHeader(MapKeys.AUTH_HEADER, MapKeys.BEARER + token);
    }

}
