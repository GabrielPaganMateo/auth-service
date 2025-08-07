package auth.papertrail.app.service.implementation;

import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.AuthInfo;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.RegisterRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.EmailService;
import auth.papertrail.app.service.interfase.JWTService;
import auth.papertrail.app.service.interfase.OTPService;
import auth.papertrail.app.service.interfase.RegisterService;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class iRegisterService implements RegisterService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final EmailService emailService;

    private final OTPService otpService;

    @Autowired
    public iRegisterService(UserRepository userRepository, JWTService jwtService, EmailService emailService, OTPService otpService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.otpService = otpService;
    }

    public AuthResponse registrationProcess(RegisterRequest request, HttpServletResponse response) {
        String email = request.getEmail();
        validateEmailFormat(email);
        checkUserAlreadyExists(email, response);
        EndUser user = saveUserWithUnverifiedStatus(email);
        setAuthHeader(user, response);
        int code = otpService.generateOTP(user);
        sendVerificationLink(user, code);
        return new AuthResponse(ResponseCode.REGISTER_OK, Details.email(email));
    }

    private void validateEmailFormat(String email) {
        if(EmailValidator.getInstance().isValid(email) == false) {
            throw new AuthException(ExceptionType.INVALID_EMAIL, Details.email(email));
        }
    }

    @Transactional
    private void checkUserAlreadyExists(String email, HttpServletResponse servletResponse) {
       Optional<EndUser> optionalEndUser = userRepository.findByEmail(email);
        if (optionalEndUser.isPresent()) { 
            EndUser endUser = optionalEndUser.get();
            if (endUser.getAuthInfo().getUserStatus() == UserStatus.CONFIRMED) {
                throw new AuthException(ExceptionType.USER_EXISTS, Details.email(email));
            } else if (endUser.getAuthInfo().getUserStatus() == UserStatus.REGISTERED) {
                otpService.sendNewOTP(endUser);
                setAuthHeader(endUser, servletResponse);
                throw new AuthException(ExceptionType.USER_UNVERIFIED, Details.email(email));
            }
        }
    }

    @Transactional
    private EndUser saveUserWithUnverifiedStatus(String email) {
        EndUser user = new EndUser(email);
        AuthInfo info = new AuthInfo(user, UserStatus.REGISTERED);
        user.setAuthInfo(info);
        return userRepository.save(user);
    }

    private String generateVerificationToken(EndUser user) {
        return jwtService.createToken(user, TokenType.VERIFICATION, Details.email(user.getEmail()));
    }

    private void sendVerificationLink(EndUser user, int code) {
        emailService.sendVerificationEmail(user, code);
    }

    private void setAuthHeader(EndUser user, HttpServletResponse servletResponse) {
        String token = generateVerificationToken(user);
        servletResponse.addHeader(MapKeys.AUTH_HEADER, MapKeys.BEARER + token);
    }
}
